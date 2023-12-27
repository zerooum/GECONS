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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/demandas")
public class DemandaController {

    @Autowired
    private DemandaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarDemanda(@RequestBody @Valid CriaDemandaDTO dados, UriComponentsBuilder uriBuilder) {

        var demanda = new DemandaEntity(dados);
        repository.save(demanda);

        var uri = uriBuilder.path("/demandas/{id}").buildAndExpand(demanda.getId()).toUri();

        return ResponseEntity.created(uri).body(demanda);

    }

    @GetMapping
    public ResponseEntity<Page<BuscaDemandasDTO>> listarDemandas(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var page = repository.findAllByAtivoTrue(paginacao).map(BuscaDemandasDTO::new);
        return ResponseEntity.ok(page);
        // paginação controlada pelo parametro size e page Ex.: /medicos?size=10&page=1
        // ordenação controlada pelo parametro sorted /medicos?sort=nome
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhaDemanda(@PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        return ResponseEntity.ok(new BuscaDemandasDTO(demanda));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity autalizarDemanda(@RequestBody @Valid BuscaDemandasDTO dados, @PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        demanda.atualizar(dados);
        return ResponseEntity.ok(new BuscaDemandasDTO(demanda));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirDemanda(@PathVariable Long id) {
        var demanda = repository.getReferenceById(id);
        System.out.println(demanda);
        demanda.excluir();
        return ResponseEntity.noContent().build();
    }
}
