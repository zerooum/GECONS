package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.fornecedores.*;
import com.saneacre.gecons.utils.RespostaSimplesDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@PreAuthorize("hasRole('FORNECEDORES') or hasRole('ADMIN')")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('FORNECEDORES_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFornecedorDTO> cadastrarFornecedor(@RequestBody @Valid CriaFornecedorDTO dados, UriComponentsBuilder uriBuilder) {
        var fornecedor = service.cadastrarFornecedor(dados);
        var uri = uriBuilder.path("/fornecedores/{id}").buildAndExpand(fornecedor.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaFornecedorDTO(fornecedor));

    }

    @GetMapping
    @PreAuthorize("hasRole('FORNECEDORES_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaFornecedorDTO>> listarFornecedores(@PageableDefault(size = 10,
            sort = {"nome"}) Pageable paginacao) {
        var pageFornecedores = service.buscaTodosFornecedores(paginacao);
        return ResponseEntity.ok(pageFornecedores);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FORNECEDORES_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFornecedorDTO> detalhaFornecedor(@PathVariable Long id) {
        var fornecedor = service.buscaFornecedorPorId(id);
        return ResponseEntity.ok(new RetornaFornecedorDTO(fornecedor));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('FORNECEDORES_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFornecedorDTO> autalizarFornecedor(@RequestBody @Valid AtualizaFornecedorDTO dados, @PathVariable Long id) {
        var fornecedor = service.atualizaFornecedor(dados, id);
        return ResponseEntity.ok(new RetornaFornecedorDTO(fornecedor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('FORNECEDORES_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> excluirFornecedor(@PathVariable Long id) {
        service.deletaContrato(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Fornecedor com id " + id + " excluido!"));
    }

    @GetMapping("/{id}/contratos")
    @PreAuthorize("hasRole('FORNECEDORES_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<List<DetalhaFornecedorContratoDTO>> detalhaFornecedorContrato(@PathVariable Long id) {
        var fornecedorContratos = service.detalhaFornecedorContrato(id);
        return ResponseEntity.ok(fornecedorContratos);
    }
}
