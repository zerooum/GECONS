package com.saneacre.gecons.domain.fornecedores;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ContratoFornecedorPoRepository contratoFornecedorPoRepository;


    public FornecedorEntity cadastrarFornecedor(CriaFornecedorDTO dados) {
        var fornecedor = new FornecedorEntity(dados);
        fornecedorRepository.save(fornecedor);
        return fornecedor;
    }

    public Page<RetornaFornecedorDTO> buscaTodosFornecedores(Pageable paginacao) {
        return fornecedorRepository.findAll(paginacao).map(RetornaFornecedorDTO::new);
    }

    public FornecedorEntity buscaFornecedorPorId(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");

        return fornecedor.get();
    }

    public FornecedorEntity atualizaFornecedor(AtualizaFornecedorDTO dados, Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");

        fornecedor.get().atualizar(dados);
        return fornecedor.get();
    }

    public void deletaFornecedor(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");

        fornecedorRepository.delete(fornecedor.get());
    }

    public List<DetalhaFornecedorContratoDTO> detalhaFornecedorContrato(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isEmpty())
            throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");

        var contratoFornecedorPo = contratoFornecedorPoRepository.findByFornecedor(fornecedor.get());
        return contratoFornecedorPo.stream().map(DetalhaFornecedorContratoDTO::new).toList();

    }
}
