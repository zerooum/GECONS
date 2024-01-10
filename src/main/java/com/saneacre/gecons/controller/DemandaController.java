package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.plano_operativo.CriaDemandaDTO;
import com.saneacre.gecons.domain.plano_operativo.BuscaDemandasDTO;
import com.saneacre.gecons.domain.plano_operativo.DemandaEntity;
import com.saneacre.gecons.domain.plano_operativo.DemandaRepository;

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
    private DemandaRepository repository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('PLANO_OPERATIVO_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity cadastrarDemanda(@RequestBody @Valid CriaDemandaDTO dados, UriComponentsBuilder uriBuilder) {

        var demanda = new DemandaEntity(dados);
        repository.save(demanda);

        var uri = uriBuilder.path("/demandas/{id}").buildAndExpand(demanda.getId()).toUri();

        return ResponseEntity.created(uri).body(demanda);

    }

    @PreAuthorize("hasRole('PLANO_OPERATIVO_VISUALIZAR') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BuscaDemandasDTO>> listarDemandas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(BuscaDemandasDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PLANO_OPERATIVO_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity detalhaDemanda(@PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        return ResponseEntity.ok(new BuscaDemandasDTO(demanda));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('PLANO_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity autalizarDemanda(@RequestBody @Valid BuscaDemandasDTO dados, @PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        demanda.atualizar(dados);
        return ResponseEntity.ok(new BuscaDemandasDTO(demanda));
    }

    @PreAuthorize("hasRole('PLANO_DELETAR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirDemanda(@PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        demanda.excluir();
        return ResponseEntity.noContent().build();
    }
}
