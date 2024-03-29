package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.empenhos.*;
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

@RestController
@RequestMapping("/empenhos")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class EmpenhoController {

    @Autowired
    EmpenhoService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaEmpenhoDTO> cadastraEmpenho(@RequestBody @Valid CriaEmpenhoDTO dados,
                                                             UriComponentsBuilder uriBuilder) {
        var empenhoCriado = service.cadastrarEmpenho(dados);
        var uri = uriBuilder.path("/empenhos/{id}").buildAndExpand(empenhoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaEmpenhoDTO(empenhoCriado));
    }

    @GetMapping
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaEmpenhoDTO>> listarEmpenhos(@PageableDefault(size = 10, sort = {"numero"}) Pageable paginacao) {
        var pageEmpenhos = service.buscaTodosEmpenhos(paginacao);
        return ResponseEntity.ok(pageEmpenhos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaEmpenhoDTO> buscaEmpenho(@PathVariable Long id) {
        var empenho = service.buscaEmpenhoPorId(id);
        return ResponseEntity.ok(empenho);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaEmpenhoDTO> atualizaEmpenho(@RequestBody @Valid AtualizaEmpenhoDTO dados, @PathVariable Long id){
        EmpenhoEntity empenhoAtualizado = service.atualizarEmpenho(id, dados);
        return ResponseEntity.ok(new RetornaEmpenhoDTO(empenhoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarEmpenho(@PathVariable Long id) {
        service.deletaEmpenho(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Empenho com id " + id + " excluido!"));
    }

}
