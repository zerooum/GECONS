package com.saneacre.gecons.domain.fornecedores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository repository;


    public FornecedorEntity cadastrarFornecedor(CriaFornecedorDTO dados) {
        var fornecedor = new FornecedorEntity(dados);
        repository.save(fornecedor);
        return fornecedor;
    }

    public Page<RetornaFornecedorDTO> buscaTodosFornecedores(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(RetornaFornecedorDTO::new);
    }

    public FornecedorEntity buscaFornecedorPorId(Long id) {
        var fornecedor = repository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            return fornecedor.get();
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }

    public FornecedorEntity atualizaFornecedor(AtualizaFornecedorDTO dados, Long id) {
        var fornecedor = repository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            fornecedor.get().atualizar(dados);
            return fornecedor.get();
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }

    public void deletaContrato(Long id) {
        var fornecedor = repository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            fornecedor.get().excluir();
            return;
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }
}
