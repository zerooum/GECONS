package com.saneacre.gecons.domain.contratos;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {

    @Autowired
    ContratoRepository repository;

    public ContratoEntity cadastrarContrato(CriaContratoDTO dados) {
        var contrato = new ContratoEntity(dados);
        repository.save(contrato);
        return contrato;
    }

    public Page<RetornaContratoDTO> buscaTodosContratos(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(RetornaContratoDTO::new);
    }

    public ContratoEntity atualizarContrato(Long id, AtualizaContratoDTO dados) {
        ContratoEntity contrato = repository.getReferenceById(id);
        if (!contrato.getAtivo()) throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
        contrato.atualizar(dados);
        return contrato;
    }

    public void deletaContrato(Long id) {
        ContratoEntity contrato = repository.getReferenceById(id);
        if (!contrato.getAtivo()) throw new EntityNotFoundException("Contrato com o id " + id + " não encontrado!");
        contrato.excluir();
    }


}
