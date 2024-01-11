package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contratos.*;
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
@RequestMapping("/contratos")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaContratoDTO> cadastraContrato(@RequestBody @Valid CriaContratoDTO dados,
                                                               UriComponentsBuilder uriBuilder) {
        var contratoCriado = service.cadastrarContrato(dados);
        var uri = uriBuilder.path("/contratos/{id}").buildAndExpand(contratoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaContratoDTO(contratoCriado));
    }

    @GetMapping
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaContratoDTO>> listarContratos(@PageableDefault(size = 10, sort = {"numero"}) Pageable paginacao) {
        var pageContratos = service.buscaTodosContratos(paginacao);
        return ResponseEntity.ok(pageContratos);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaContratoDTO> atualizaContrato(@RequestBody @Valid AtualizaContratoDTO dados, @PathVariable Long id){
        ContratoEntity contratoAtualizado = service.atualizarContrato(id, dados);
        return ResponseEntity.ok().body(new RetornaContratoDTO(contratoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity deletarContrato(@PathVariable Long id) {
        service.deletaContrato(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Contrato com id " + id + " excluido!"));
    }






}
