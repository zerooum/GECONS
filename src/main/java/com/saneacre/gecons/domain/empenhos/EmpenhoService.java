package com.saneacre.gecons.domain.empenhos;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.ContratoRepository;
import com.saneacre.gecons.domain.contratos.contrato_elemento.ContratoElementoId;
import com.saneacre.gecons.domain.contratos.contrato_elemento.ContratoElementoRepository;
import com.saneacre.gecons.domain.contratos.contrato_fonte.ContratoFonteId;
import com.saneacre.gecons.domain.contratos.contrato_fonte.ContratoFonteRepository;
import com.saneacre.gecons.domain.contratos.contrato_programa.ContratoProgramaId;
import com.saneacre.gecons.domain.contratos.contrato_programa.ContratoProgramaRepository;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaEntity;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaRepository;
import com.saneacre.gecons.domain.contratos.fontes.FonteEntity;
import com.saneacre.gecons.domain.contratos.fontes.FonteRepository;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoEntity;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmpenhoService {

    @Autowired
    EmpenhoRepository empenhoRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    ProgramaDeTrabalhoRepository programaDeTrabalhoRepository;

    @Autowired
    ElementoDeDespesaRepository elementoDeDespesaRepository;

    @Autowired
    FonteRepository fonteRepository;

    @Autowired
    ContratoProgramaRepository contratoProgramaRepository;

    @Autowired
    ContratoElementoRepository contratoElementoRepository;

    @Autowired
    ContratoFonteRepository contratoFonteRepository;

    public EmpenhoEntity cadastrarEmpenho(CriaEmpenhoDTO dados) {
        var contrato = validaContrato(dados.idContrato());
        var programa = validaPrograma(dados.idPrograma(), contrato);
        var elemento = validaElemento(dados.idElemento(), contrato);
        var fonte = validaFonte(dados.idFonte(), contrato);

        if (verificaValorTotal(contrato, dados.valor())) {
            throw new DataIntegrityViolationException("Impossível adicionar empenho, " +
                                                        "o valor inserido ultrapassa o valor total do contrato");
        }

        var empenho = new EmpenhoEntity(0, contrato, programa, elemento, fonte, dados.numero(), dados.valor(),
                                        dados.data(), dados.descricao());
        empenhoRepository.save(empenho);
        return empenho;
    }

    public Page<RetornaEmpenhoDTO> buscaTodosEmpenhos(Pageable paginacao) {
        return empenhoRepository.findAll(paginacao).map(RetornaEmpenhoDTO::new);
    }

    public RetornaEmpenhoDTO buscaEmpenhoPorId(Long id) {
        var empenho = empenhoRepository.findById(id);
        if (empenho.isEmpty())
            throw new EntityNotFoundException("Empenho com o id " + id + " não encontrado!");

        return new RetornaEmpenhoDTO(empenho.get());
    }

    public EmpenhoEntity atualizarEmpenho(Long id, AtualizaEmpenhoDTO dados) {
        var empenho = empenhoRepository.findById(id);
        if (empenho.isEmpty())
            throw new EntityNotFoundException("Empenho com o id " + id + " não encontrado!");

        if (verificaValorTotal(empenho.get().getContrato(), dados.valor())) {
            throw new DataIntegrityViolationException("Impossível atualizar empenho, " +
                                                         "o valor inserido ultrapassa o valor total do contrato");
        }

        empenho.get().atualizar(dados);
        return empenho.get();
    }

    public void deletaEmpenho(Long id) {
        var empenho = empenhoRepository.findById(id);
        if (empenho.isEmpty())
            throw new EntityNotFoundException("Empenho com o id " + id + " não encontrado!");

        // VERIFICAR SE O EMPENHO EXISTE EM ALGUMA COMPRA

        empenhoRepository.delete(empenho.get());
    }

    private ContratoEntity validaContrato(Long idContrato) {
        var contrato = contratoRepository.findById(idContrato);
        if (contrato.isEmpty() || !contrato.get().getAtivo())
            throw new EntityNotFoundException("Contrato com o id " + idContrato + " não encontrado!");
        return contrato.get();
    }

    private ProgramaDeTrabalhoEntity validaPrograma(Long idPrograma, ContratoEntity contrato) {
        var programa = programaDeTrabalhoRepository.findById(idPrograma);
        if (programa.isEmpty() || !programa.get().getAtivo())
            throw new EntityNotFoundException("Programa de trabalho com o id " + idPrograma + " não encontrado!");

        var contratoPrograma = contratoProgramaRepository.findById(new ContratoProgramaId(contrato.getId(), programa.get().getId()));
        if (contratoPrograma.isEmpty())
            throw new DataIntegrityViolationException("O empenho não pode ser realizado, " +
                                                        "pois o programa de trabalho não existe no contrato!");

        return programa.get();
    }

    private ElementoDeDespesaEntity validaElemento(Long idElemento, ContratoEntity contrato) {
        var elemento = elementoDeDespesaRepository.findById(idElemento);
        if (elemento.isEmpty() || !elemento.get().getAtivo())
            throw new EntityNotFoundException("Elemento de despesa com o id " + idElemento + " não encontrado!");

        var contratoElemento = contratoElementoRepository.findById(new ContratoElementoId(contrato.getId(), elemento.get().getId()));
        if (contratoElemento.isEmpty())
            throw new DataIntegrityViolationException("O empenho não pode ser realizado, " +
                                                        "pois o elemento de despesa não existe no contrato!");

        return elemento.get();
    }

    private FonteEntity validaFonte(Long idFonte, ContratoEntity contrato) {
        var fonte = fonteRepository.findById(idFonte);
        if (fonte.isEmpty() || !fonte.get().getAtivo())
            throw new EntityNotFoundException("Fonte com o id " + idFonte + " não encontrada!");

        var contratoFonte = contratoFonteRepository.findById(new ContratoFonteId(contrato.getId(), fonte.get().getId()));
        if (contratoFonte.isEmpty())
            throw new DataIntegrityViolationException("O empenho não pode ser realizado, " +
                                                        "pois a fonte não existe no contrato!");
        return fonte.get();
    }

    private boolean verificaValorTotal(ContratoEntity contrato, BigDecimal valorAdicionado) {
        var empenhos = empenhoRepository.findByContrato(contrato);
        BigDecimal valorAtual = empenhos.stream().map(EmpenhoEntity::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal novoValor = valorAtual.add(valorAdicionado);
        return (novoValor.compareTo(contrato.getValor()) > 0);
    }

}
