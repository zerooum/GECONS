package com.saneacre.gecons.domain.contratos;

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

    public ContratoEntity cadastrarContrato(CriaContratoDTO dados) {
        var contrato = new ContratoEntity(dados);
        contratoRepository.save(contrato);
        return contrato;
    }

    public Page<RetornaContratoDTO> buscaTodosContratos(Pageable paginacao) {
        return contratoRepository.findAllByAtivoTrue(paginacao).map(RetornaContratoDTO::new);
    }

    public RetornaContratoDTO buscaContratoPorId(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        return new RetornaContratoDTO(contrato.get());
    }

    public ContratoEntity atualizarContrato(Long id, AtualizaContratoDTO dados) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
        contrato.get().atualizar(dados);
        return contrato.get();
    }

    public void deletaContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        contrato.get().excluir();
    }

    // Itens do contrato
    public ContratoFornecedorPoEntity getContratoFornecedorPo(ItemNoContratoDTO dados) {
        var contrato = contratoRepository.findByNumero(dados.contrato());
        if (contrato == null || !contrato.getAtivo())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var fornecedor = fornecedorRepository.findByNome(dados.fornecedor());
        if (fornecedor == null || !contrato.getAtivo())
            throw new EntityNotFoundException("O fornecedor informado não está cadastrado!");

        var demanda = demandaRepository.findByNome(dados.demanda());
        if (demanda == null || !contrato.getAtivo())
            throw new EntityNotFoundException("A demanda informada não está cadastrada!");

        if (dados.quant_consumo() != null && dados.quant_consumo().compareTo(dados.quant_registro()) > 0)
            throw new ConsumoMaiorQueRegistroException();

        ContratoFornecedorPoId id = new ContratoFornecedorPoId(contrato.getId(), fornecedor.getId(), demanda.getId());
        return new ContratoFornecedorPoEntity(id, contrato, fornecedor, demanda, dados.quant_consumo(),
                                                dados.quant_registro(), dados.valor_unitario());
    }

    public ContratoFornecedorPoEntity adicionaItemNoContrato(ItemNoContratoDTO dados) {
        var contratoFornecedorPo = getContratoFornecedorPo(dados);

        var jaExiste = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (jaExiste.isPresent()) throw new DataIntegrityViolationException("O item já esta adicionado ao contrato!");

        //verifica se o valor do item ultrapassa o valor total do contrato
        verificaValorContrato(contratoFornecedorPo);

        contratoFornecedorPoRepository.save(contratoFornecedorPo);
        return contratoFornecedorPo;
    }

    public void removeItemNoContrato(ItemNoContratoDTO dados) {
        var contratoFornecedorPo = getContratoFornecedorPo(dados);

        var jaExiste = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (jaExiste.isEmpty()) throw new EntityNotFoundException("O item não está presente no contrato!");

        // ADICIONAR LOGICA PARA VERIFICAR SE JA EXISTE COMPRAS DO ITEM ANTES DE REMOVER

        contratoFornecedorPoRepository.delete(contratoFornecedorPo);
    }

    private void verificaValorContrato(ContratoFornecedorPoEntity contratoFornecedorPo) {
        //calcula valor atual de todos os itens do contrato
        var contratos = contratoFornecedorPoRepository.findByContrato(contratoFornecedorPo.getContrato());
        BigDecimal valorTotalAtual = BigDecimal.ZERO;
        for (var contrato : contratos) {
            BigDecimal valorItem = contrato.getQuantRegistro().multiply(contrato.getValorUnitario());
            System.out.println(valorItem);
            valorTotalAtual = valorTotalAtual.add(valorItem);
        }

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
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoItens = contratoFornecedorPoRepository.findByContrato(contrato.get());
        return contratoItens.stream().map(ItensContratoDTO::new).toList();

    }

    //Programas do contrato
    public ContratoProgramaEntity getContratoPrograma(ProgramaNoContratoDTO dados) {
        var contrato = contratoRepository.findByNumero(dados.contrato());
        if (contrato == null || !contrato.getAtivo())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var programa = programaDeTrabalhoRepository.findByNumero(dados.programa());
        if (programa == null || !programa.getAtivo())
            throw new EntityNotFoundException("O programa de trabalho informado não está cadastrado!");

        ContratoProgramaId id = new ContratoProgramaId(contrato.getId(), programa.getId());
        return new ContratoProgramaEntity(id, contrato, programa);
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

        // ADICIONAR LOGICA PARA VERIFICAR SE JA EXISTE EMPENHO NO PROGRAMA ANTES DE REMOVER

        contratoProgramaRepository.delete(contratoPrograma);
    }

    public List<ProgramasContratoDTO> buscaProgramasDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoProgramas = contratoProgramaRepository.findByContrato(contrato.get());
        return contratoProgramas.stream().map(ProgramasContratoDTO::new).toList();
    }

    //Elementos no contrato
    public ContratoElementoEntity getContratoElemento(ElementoNoContratoDTO dados) {
        var contrato = contratoRepository.findByNumero(dados.contrato());
        if (contrato == null || !contrato.getAtivo())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var elemento = elementoDeDespesaRepository.findByNumero(dados.elemento());
        if (elemento == null || !elemento.getAtivo())
            throw new EntityNotFoundException("O elemento de despesa informado não está cadastrado!");

        ContratoElementoId id = new ContratoElementoId(contrato.getId(), elemento.getId());
        return new ContratoElementoEntity(id, contrato, elemento);
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

        // ADICIONAR LOGICA PARA VERIFICAR SE JA EXISTE EMPENHO NO PROGRAMA ANTES DE REMOVER

        contratoElementoRepository.delete(contratoElemento);
    }

    public List<ElementosContratoDTO> buscaElementosDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoElementos = contratoElementoRepository.findByContrato(contrato.get());
        return contratoElementos.stream().map(ElementosContratoDTO::new).toList();
    }

    //Fontes no contrato
    public ContratoFonteEntity getFonteElemento(FonteNoContratoDTO dados) {
        var contrato = contratoRepository.findByNumero(dados.contrato());
        if (contrato == null || !contrato.getAtivo())
            throw new EntityNotFoundException("O contrato informado não está cadastrado!");

        var fonte = fonteRepository.findByNumero(dados.fonte());
        if (fonte == null || !fonte.getAtivo())
            throw new EntityNotFoundException("A fonte informada não está cadastrada!");

        ContratoFonteId id = new ContratoFonteId(contrato.getId(), fonte.getId());
        return new ContratoFonteEntity(id, contrato, fonte);
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

        // ADICIONAR LOGICA PARA VERIFICAR SE JA EXISTE EMPENHO NO PROGRAMA ANTES DE REMOVER

        contratoFonteRepository.delete(contratoFonte);
    }

    public List<FontesContratoDTO> buscaFontesDoContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");

        var contratoFontes = contratoFonteRepository.findByContrato(contrato.get());
        return contratoFontes.stream().map(FontesContratoDTO::new).toList();
    }


}
