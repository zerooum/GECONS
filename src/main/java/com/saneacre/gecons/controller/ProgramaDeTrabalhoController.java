package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.programa_de_trabalho.CriaProgramaDeTrabalhoDTO;
import com.saneacre.gecons.domain.programa_de_trabalho.ProgramaDeTrabalhoService;
import com.saneacre.gecons.domain.programa_de_trabalho.RetornaProgramaDeTrabalhoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/programas-de-trabalho")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class ProgramaDeTrabalhoController {

    @Autowired
    ProgramaDeTrabalhoService programaDeTrabalhoService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaProgramaDeTrabalhoDTO> criaProgramaDeTrabalho(@RequestBody @Valid CriaProgramaDeTrabalhoDTO dados, UriComponentsBuilder uriBuilder) {
        var programaCriado = programaDeTrabalhoService.criaProgramaDeTrabalho(dados);
        var uri = uriBuilder.path("/programas-de-trabalho/{id}").buildAndExpand(programaCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaProgramaDeTrabalhoDTO(programaCriado));
    }


}
