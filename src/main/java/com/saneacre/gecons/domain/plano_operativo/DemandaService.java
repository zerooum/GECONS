package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandaService {

    @Autowired
    DemandaRepository demandaRepository;

    @Autowired
    ContratoFornecedorPoRepository contratoFornecedorPoRepository;

    public DemandaEntity cadastrarDemanda(CriaDemandaDTO dados) {
        var demanda = new DemandaEntity(dados);
        demandaRepository.save(demanda);
        return demanda;
    }

    public Page<RetornaDemandaDTO> buscaTodasDemandas(Pageable paginacao) {
        return demandaRepository.findAll(paginacao).map(RetornaDemandaDTO::new);
    }

    public DemandaEntity buscaDemandaPorId(Long id) {
        var demanda = demandaRepository.findById(id);
        if (demanda.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        return demanda.get();
    }

    public DemandaEntity atualizarDemanda(AtualizaDemandaDTO dados, Long id) {
        var demanda = demandaRepository.findById(id);
        if (demanda.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        demanda.get().atualizar(dados);
        return demanda.get();

    }

    public void excluirDemanda(Long id) {
        var demanda = demandaRepository.findById(id);
        if (demanda.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        demandaRepository.delete(demanda.get());
    }

    public List<DetalhaDemandaContratoDTO> detalhaDemandaContratos(Long id) {
        var demanda = demandaRepository.findById(id);
        if (demanda.isEmpty())
            throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");

        var contratoFornecedorPo = contratoFornecedorPoRepository.findByDemanda(demanda.get());
        return contratoFornecedorPo.stream().map(DetalhaDemandaContratoDTO::new).toList();
    }

}