package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.plano_operativo.*;

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
@RequestMapping("/demandas")
@PreAuthorize("hasRole('PLANO_OPERATIVO') or hasRole('ADMIN')")
public class DemandaController {

    @Autowired
    private DemandaService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('PLANO_OPERATIVO_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaDemandaDTO> cadastrarDemanda(@RequestBody @Valid CriaDemandaDTO dados, UriComponentsBuilder uriBuilder) {
        var demanda = service.cadastrarDemanda(dados);
        var uri = uriBuilder.path("/demandas/{id}").buildAndExpand(demanda.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaDemandaDTO(demanda));
    }

    @PreAuthorize("hasRole('PLANO_OPERATIVO_VISUALIZAR') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<RetornaDemandaDTO>> listarDemandas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var pageDemandas = service.buscaTodasDemandas(paginacao);
        return ResponseEntity.ok(pageDemandas);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PLANO_OPERATIVO_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaDemandaDTO> detalhaDemanda(@PathVariable Long id) {
        var demanda = service.buscaDemandaPorId(id);
        return ResponseEntity.ok(new RetornaDemandaDTO(demanda));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('PLANO_OPERATIVO_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaDemandaDTO> autalizarDemanda(@RequestBody @Valid AtualizaDemandaDTO dados, @PathVariable Long id) {
        var demandaAtualizada = service.atualizarDemanda(dados, id);
        return ResponseEntity.ok(new RetornaDemandaDTO(demandaAtualizada));
    }

    @PreAuthorize("hasRole('PLANO_OPERATIVO_DELETAR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<RespostaSimplesDTO> excluirDemanda(@PathVariable Long id) {
        service.excluirDemanda(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Contrato com id " + id + " excluido!"));
    }
}
