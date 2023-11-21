package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.fornecedores.BuscaFornecedoresDTO;
import com.saneacre.gecons.domain.fornecedores.CriaFornecedorDTO;
import com.saneacre.gecons.domain.fornecedores.FornecedorEntity;
import com.saneacre.gecons.domain.fornecedores.FornecedorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarFornecedor(@RequestBody @Valid CriaFornecedorDTO dados, UriComponentsBuilder uriBuilder) {

        var fornecedor = new FornecedorEntity(dados);
        repository.save(fornecedor);

        var uri = uriBuilder.path("/fornecedores/{id}").buildAndExpand(fornecedor.getId()).toUri();

        return ResponseEntity.created(uri).body(fornecedor);

    }

    @GetMapping
    public ResponseEntity<Page<BuscaFornecedoresDTO>> listarFornecedores(@PageableDefault(size = 10,
            sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(BuscaFornecedoresDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhaFornecedor(@PathVariable Long id) {
        var fornecedor = repository.getReferenceById(id);
        return ResponseEntity.ok(new BuscaFornecedoresDTO(fornecedor));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity autalizarFornecedor(@RequestBody @Valid BuscaFornecedoresDTO dados, @PathVariable Long id) {
        var fornecedor = repository.getReferenceById(id);
        fornecedor.atualizar(dados);
        return ResponseEntity.ok(new BuscaFornecedoresDTO(fornecedor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirFornecedor(@PathVariable Long id) {
        var fornecedor = repository.getReferenceById(id);
        fornecedor.excluir();
        return ResponseEntity.noContent().build();
    }
}
