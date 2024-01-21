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
        return fornecedorRepository.findAllByAtivoTrue(paginacao).map(RetornaFornecedorDTO::new);
    }

    public FornecedorEntity buscaFornecedorPorId(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            return fornecedor.get();
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }

    public FornecedorEntity atualizaFornecedor(AtualizaFornecedorDTO dados, Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            fornecedor.get().atualizar(dados);
            return fornecedor.get();
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }

    public void deletaContrato(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            if (!fornecedor.get().getAtivo()) throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
            fornecedor.get().excluir();
            return;
        }
        throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");
    }

    public List<DetalhaFornecedorContratoDTO> detalhaFornecedorContrato(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isEmpty() || !fornecedor.get().getAtivo())
            throw new EntityNotFoundException("Fornecedor com o id " + id + " não encontrado!");

        var contratoFornecedorPo = contratoFornecedorPoRepository.findByFornecedor(fornecedor.get());
        return contratoFornecedorPo.stream().map(DetalhaFornecedorContratoDTO::new).toList();

    }
}
