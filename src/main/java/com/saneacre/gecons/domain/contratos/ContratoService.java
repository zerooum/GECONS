package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.compras.itens.CompraItemRepository;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoId;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ItemNoContratoDTO;
import com.saneacre.gecons.domain.contratos.contrato_elemento.*;
import com.saneacre.gecons.domain.contratos.contrato_fonte.*;
import com.saneacre.gecons.domain.contratos.contrato_programa.*;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaRepository;
import com.saneacre.gecons.domain.contratos.fontes.FonteRepository;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoRepository;
import com.saneacre.gecons.domain.empenhos.EmpenhoRepository;
import com.saneacre.gecons.domain.fornecedores.FornecedorRepository;
import com.saneacre.gecons.domain.plano_operativo.DemandaRepository;
import com.saneacre.gecons.infra.erros.ConsumoMaiorQueRegistroException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    DemandaRepository demandaRepository;

    @Autowired
    ContratoFornecedorPoRepository contratoFornecedorPoRepository;

    @Autowired
    ProgramaDeTrabalhoRepository programaDeTrabalhoRepository;

    @Autowired
    ContratoProgramaRepository contratoProgramaRepository;

    @Autowired
    ElementoDeDespesaRepository elementoDeDespesaRepository;

    @Autowired
    ContratoElementoRepository contratoElementoRepository;

    @Autowired
    FonteRepository fonteRepository;

    @Autowired
    ContratoFonteRepository contratoFonteRepository;

    @Autowired
    EmpenhoRepository empenhoRepository;

    @Autowired
    CompraItemRepository compraItemRepository;

    public ContratoEntity cadastrarContrato(CriaContratoDTO dados) {
        var contrato = new ContratoEntity(dados);
        contratoRepository.save(contrato);
        return contrato;
    }

    public Page<RetornaContratoDTO> buscaTodosContratos(Pageable paginacao) {
        return contratoRepository.findAll(paginacao).map(RetornaContratoDTO::new);
    }

    public RetornaContratoDTO buscaContratoPorId(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        return new RetornaContratoDTO(contrato.get());
    }

    public ContratoEntity atualizarContrato(Long id, AtualizaContratoDTO dados) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
        contrato.get().atualizar(dados);
        return contrato.get();
    }

    public void deletaContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        contratoRepository.delete(contrato.get());
    }

    // Itens do contrato
    private ContratoFornecedorPoEntity getContratoFornecedorPo(ItemNoContratoDTO dados) {
        var contrato = contratoRepository.findById(dados.idContrato());
        if (contrato.isEmpty())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var fornecedor = fornecedorRepository.findById(dados.idFornecedor());
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("O fornecedor informado não está cadastrado!");

        var demanda = demandaRepository.findById(dados.idDemanda());
        if (demanda.isEmpty())
            throw new EntityNotFoundException("A demanda informada não está cadastrada!");

        if (dados.quant_consumo() != null && dados.quant_consumo().compareTo(dados.quant_registro()) > 0)
            throw new ConsumoMaiorQueRegistroException();

        ContratoFornecedorPoId id = new ContratoFornecedorPoId(contrato.get().getId(), fornecedor.get().getId(),
                demanda.get().getId());

        return new ContratoFornecedorPoEntity(id, contrato.get(), fornecedor.get(), demanda.get(), dados.quant_consumo(),
                                                dados.quant_registro(), dados.valor_unitario());
    }

    public void adicionaItemNoContrato(ItemNoContratoDTO dados) {
        var contratoFornecedorPo = getContratoFornecedorPo(dados);

        var jaExiste = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (jaExiste.isPresent()) throw new DataIntegrityViolationException("O item já esta adicionado ao contrato!");

        //verifica se o valor do item ultrapassa o valor total do contrato
        verificaValorContrato(contratoFornecedorPo);

        contratoFornecedorPoRepository.save(contratoFornecedorPo);
    }

    public void removeItemNoContrato(ItemNoContratoDTO dados) {
        var contratoFornecedorPo = getContratoFornecedorPo(dados);

        var itemNoContrato = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (itemNoContrato.isEmpty())
            throw new EntityNotFoundException("O item não está presente no contrato!");

        // Verifica se ja existe compra do item a ser excluido
        var comprasDoItem = compraItemRepository.findAllByContratoAndFornecedorAndDemanda(
                contratoFornecedorPo.getContrato(),
                contratoFornecedorPo.getFornecedor(),
                contratoFornecedorPo.getDemanda()
        );
        if (!comprasDoItem.isEmpty())
            throw new DataIntegrityViolationException("O item não pode ser excluído, pois ja existem compras associadas a este!");

        contratoFornecedorPoRepository.delete(contratoFornecedorPo);
    }

    private void verificaValorContrato(ContratoFornecedorPoEntity contratoFornecedorPo) {
        //calcula valor atual de todos os itens do contrato
        var contratos = contratoFornecedorPoRepository.findByContrato(contratoFornecedorPo.getContrato());
        BigDecimal valorTotalAtual = contratos.stream()
                .map(contrato -> contrato.getQuantRegistro().multiply(contrato.getValorUnitario()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //calcula valor do item a ser adicionado
        var quantItemAdicionado = contratoFornecedorPo.getQuantRegistro();
        var valorUnitarioItemAdicionado = contratoFornecedorPo.getValorUnitario();
        var valorItemAdicionado = quantItemAdicionado.multiply(valorUnitarioItemAdicionado);

        //compara se a adição do novo item ultrapassa o valor do contrato
        var novoValorTotal = valorTotalAtual.add(valorItemAdicionado);
        if (novoValorTotal.compareTo(contratoFornecedorPo.getContrato().getValor()) > 0)
            throw new DataIntegrityViolationException("Impossível adicionar/alterar item no contrato, " +
                    "o item inserido ultrapassa o valor total");
    }

    public List<ItensContratoDTO> buscaItensDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoItens = contratoFornecedorPoRepository.findByContrato(contrato.get());
        return contratoItens.stream().map(ItensContratoDTO::new).toList();

    }

    //Programas do contrato
    private ContratoProgramaEntity getContratoPrograma(ProgramaNoContratoDTO dados) {
        var contrato = contratoRepository.findById(dados.idContrato());
        if (contrato.isEmpty())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var programa = programaDeTrabalhoRepository.findById(dados.idPrograma());
        if (programa.isEmpty())
            throw new EntityNotFoundException("O programa de trabalho informado não está cadastrado!");

        ContratoProgramaId id = new ContratoProgramaId(contrato.get().getId(), programa.get().getId());
        return new ContratoProgramaEntity(id, contrato.get(), programa.get());
    }

    public void adicionaProgramaNoContrato(ProgramaNoContratoDTO dados) {
        var contratoPrograma = getContratoPrograma(dados);

        var jaExiste = contratoProgramaRepository.procuraChaveDuplicada(contratoPrograma.getId());
        if (jaExiste.isPresent())
            throw new DataIntegrityViolationException("O programa de trabalho já esta adicionado ao contrato!");

        contratoProgramaRepository.save(contratoPrograma);
    }

    public void removeProgramaNoContrato(ProgramaNoContratoDTO dados) {
        var contratoPrograma = getContratoPrograma(dados);

        var jaExiste = contratoProgramaRepository.procuraChaveDuplicada(contratoPrograma.getId());
        if (jaExiste.isEmpty())
            throw new EntityNotFoundException("O programa de trabalho não está presente no contrato!");

        // Verifica se ja existe empenho para o programa no contrato
        var empenhos = empenhoRepository.findByContratoAndPrograma(contratoPrograma.getContrato(),
                                                                    contratoPrograma.getPrograma());
        if (!empenhos.isEmpty())
            throw new DataIntegrityViolationException("Não é possível remover o programa do contrato, " +
                                                      "pois já existem empenhos associados!");

        contratoProgramaRepository.delete(contratoPrograma);
    }

    public List<ProgramasContratoDTO> buscaProgramasDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoProgramas = contratoProgramaRepository.findByContrato(contrato.get());
        return contratoProgramas.stream().map(ProgramasContratoDTO::new).toList();
    }

    //Elementos no contrato
    public ContratoElementoEntity getContratoElemento(ElementoNoContratoDTO dados) {
        var contrato = contratoRepository.findById(dados.idContrato());
        if (contrato.isEmpty())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var elemento = elementoDeDespesaRepository.findById(dados.idElemento());
        if (elemento.isEmpty())
            throw new EntityNotFoundException("O elemento de despesa informado não está cadastrado!");

        ContratoElementoId id = new ContratoElementoId(contrato.get().getId(), elemento.get().getId());
        return new ContratoElementoEntity(id, contrato.get(), elemento.get());
    }

    public void adicionaElementoNoContrato(ElementoNoContratoDTO dados) {
        var contratoElemento = getContratoElemento(dados);

        var jaExiste = contratoElementoRepository.procuraChaveDuplicada(contratoElemento.getId());
        if (jaExiste.isPresent())
            throw new DataIntegrityViolationException("O elemento de despesa já esta adicionado ao contrato!");

        contratoElementoRepository.save(contratoElemento);
    }

    public void removeElementoNoContrato(ElementoNoContratoDTO dados) {
        var contratoElemento = getContratoElemento(dados);

        var jaExiste = contratoElementoRepository.procuraChaveDuplicada(contratoElemento.getId());
        if (jaExiste.isEmpty())
            throw new EntityNotFoundException("O elemento de despesa não está presente no contrato!");

        // Verifica se ja existe empenho para o programa no contrato
        var empenhos = empenhoRepository.findByContratoAndElemento(contratoElemento.getContrato(),
                contratoElemento.getElemento());
        if (!empenhos.isEmpty())
            throw new DataIntegrityViolationException("Não é possível remover o elemento do contrato, " +
                    "pois já existem empenhos associados!");

        contratoElementoRepository.delete(contratoElemento);
    }

    public List<ElementosContratoDTO> buscaElementosDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoElementos = contratoElementoRepository.findByContrato(contrato.get());
        return contratoElementos.stream().map(ElementosContratoDTO::new).toList();
    }

    //Fontes no contrato
    public ContratoFonteEntity getFonteElemento(FonteNoContratoDTO dados) {
        var contrato = contratoRepository.findById(dados.idContrato());
        if (contrato.isEmpty())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var fonte = fonteRepository.findById(dados.idFonte());
        if (fonte.isEmpty())
            throw new EntityNotFoundException("A fonte informada não está cadastrada!");

        ContratoFonteId id = new ContratoFonteId(contrato.get().getId(), fonte.get().getId());
        return new ContratoFonteEntity(id, contrato.get(), fonte.get());
    }

    public void adicionaFonteNoContrato(FonteNoContratoDTO dados) {
        var contratoFonte = getFonteElemento(dados);

        var jaExiste = contratoFonteRepository.procuraChaveDuplicada(contratoFonte.getId());
        if (jaExiste.isPresent())
            throw new DataIntegrityViolationException("A fonte já esta adicionada ao contrato!");

        contratoFonteRepository.save(contratoFonte);
    }

    public void removeFonteNoContrato(FonteNoContratoDTO dados) {
        var contratoFonte = getFonteElemento(dados);

        var jaExiste = contratoFonteRepository.procuraChaveDuplicada(contratoFonte.getId());
        if (jaExiste.isEmpty())
            throw new EntityNotFoundException("A fonte não está presente no contrato!");

        // Verifica se ja existe empenho para o programa no contrato
        var empenhos = empenhoRepository.findByContratoAndFonte(contratoFonte.getContrato(),
                contratoFonte.getFonte());
        if (!empenhos.isEmpty())
            throw new DataIntegrityViolationException("Não é possível remover a fonte do contrato, " +
                    "pois já existem empenhos associados!");

        contratoFonteRepository.delete(contratoFonte);
    }

    public List<FontesContratoDTO> buscaFontesDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoFontes = contratoFonteRepository.findByContrato(contrato.get());
        return contratoFontes.stream().map(FontesContratoDTO::new).toList();
    }

    //Empenhos no contrato
    public List<EmpenhosContratoDTO> buscaEmpenhosDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoEmpenhos = empenhoRepository.findByContrato(contrato.get());
        return contratoEmpenhos.stream().map(EmpenhosContratoDTO::new).toList();
    }

}
