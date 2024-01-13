package com.saneacre.gecons.domain.plano_operativo;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandaService {

    @Autowired
    DemandaRepository repository;

    public DemandaEntity cadastrarDemanda(CriaDemandaDTO dados) {
        var demanda = new DemandaEntity(dados);
        repository.save(demanda);
        return demanda;
    }

    public Page<RetornaDemandaDTO> buscaTodasDemandas(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(RetornaDemandaDTO::new);
    }

    public DemandaEntity buscaDemandaPorId(Long id) {
        var demanda = repository.findById(id);
        if (demanda.isPresent()) {
            if (!demanda.get().getAtivo()) throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
            return demanda.get();
        }
        throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
    }

    public DemandaEntity atualizarDemanda(AtualizaDemandaDTO dados, Long id) {
        var demanda = repository.findById(id);
        if (demanda.isPresent()) {
            if (!demanda.get().getAtivo()) throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
            demanda.get().atualizar(dados);
            return demanda.get();
        }
        throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
    }

    public void excluirDemanda(Long id) {
        var demanda = repository.findById(id);
        if (demanda.isPresent()) {
            if (!demanda.get().getAtivo()) throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
            demanda.get().excluir();
            return;
        }
        throw new EntityNotFoundException("Item com o id " + id + " não encontrado!");
    }
}
