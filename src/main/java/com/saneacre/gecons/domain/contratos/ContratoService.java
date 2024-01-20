package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoId;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import com.saneacre.gecons.domain.contrato_fornecedor_po.ItemNoContratoDTO;
import com.saneacre.gecons.domain.fornecedores.FornecedorRepository;
import com.saneacre.gecons.domain.plano_operativo.DemandaRepository;
import com.saneacre.gecons.infra.erros.ConsumoMaiorQueRegistroException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public ContratoEntity cadastrarContrato(CriaContratoDTO dados) {
        var contrato = new ContratoEntity(dados);
        contratoRepository.save(contrato);
        return contrato;
    }

    public Page<RetornaContratoDTO> buscaTodosContratos(Pageable paginacao) {
        return contratoRepository.findAllByAtivoTrue(paginacao).map(RetornaContratoDTO::new);
    }

    public ContratoEntity atualizarContrato(Long id, AtualizaContratoDTO dados) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isPresent()) {
            if (!contrato.get().getAtivo()) throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
            return contrato.get();
        }
        throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
    }

    public void deletaContrato(Long id) {
        var contrato = contratoRepository.findById(id);
        if (contrato.isPresent()) {
            if (!contrato.get().getAtivo()) throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
            contrato.get().excluir();
            return;
        }
        throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
    }


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

        //verifica se o valor do item ultrapassa o valor total do contrato

        var jaExiste = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (jaExiste.isPresent()) throw new DataIntegrityViolationException("O item já esta adicionado ao contrato!");

        contratoFornecedorPoRepository.save(contratoFornecedorPo);
        return contratoFornecedorPo;
    }

    public void removeItemNoContrato(ItemNoContratoDTO dados) {
        var contratoFornecedorPo = getContratoFornecedorPo(dados);

        var jaExiste = contratoFornecedorPoRepository.procuraChaveDuplicada(contratoFornecedorPo.getId());
        if (jaExiste.isEmpty()) throw new EntityNotFoundException("O item não está presente no contrato!");

        contratoFornecedorPoRepository.delete(contratoFornecedorPo);
    }
}
