package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contratos.BuscaContratoDTO;
import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.ContratoRepository;
import com.saneacre.gecons.domain.contratos.CriaContratoDTO;
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
@RequestMapping("/contratos")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class ContratoController {

    @Autowired
    private ContratoRepository repository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity cadastraContrato(@RequestBody @Valid CriaContratoDTO dados, UriComponentsBuilder uriBuilder) {

        var contrato = new ContratoEntity(dados);
        repository.save(contrato);

        var uri = uriBuilder.path("/contratos/{id}").buildAndExpand(contrato.getId()).toUri();

        return ResponseEntity.created(uri).body(contrato);
    }

    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BuscaContratoDTO>> listarContratos(@PageableDefault(size = 10, sort = {"numero"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(BuscaContratoDTO::new);
        return ResponseEntity.ok(page);
    }

}
