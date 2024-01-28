package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contratos.programa_de_trabalho.AtualizaProgramaDeTrabalhoDTO;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.CriaProgramaDeTrabalhoDTO;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoService;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.RetornaProgramaDeTrabalhoDTO;
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
@RequestMapping("/programas-de-trabalho")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class ProgramaDeTrabalhoController {

    @Autowired
    ProgramaDeTrabalhoService programaDeTrabalhoService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaProgramaDeTrabalhoDTO> criaProgramaDeTrabalho(@RequestBody @Valid CriaProgramaDeTrabalhoDTO dados,
                                                                               UriComponentsBuilder uriBuilder) {
        var programaCriado = programaDeTrabalhoService.criaProgramaDeTrabalho(dados);
        var uri = uriBuilder.path("/programas-de-trabalho/{id}").buildAndExpand(programaCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaProgramaDeTrabalhoDTO(programaCriado));
    }

    @GetMapping
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaProgramaDeTrabalhoDTO>> listaProgramasDeTrabalho(@PageableDefault(size = 10, sort = {"id"})
                                                                                           Pageable paginacao) {
        var pageProgramas = programaDeTrabalhoService.buscarTodosProgramas(paginacao);
        return ResponseEntity.ok(pageProgramas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaProgramaDeTrabalhoDTO> detalhaProgramaDeTrabalho(@PathVariable Long id) {
        var programa = programaDeTrabalhoService.buscaProgramaPorId(id);
        return ResponseEntity.ok(programa);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaProgramaDeTrabalhoDTO> atualizaProgramaDeTrabalho(@RequestBody @Valid AtualizaProgramaDeTrabalhoDTO dados,
                                                                                    @PathVariable Long id) {
        var programaAtualizado = programaDeTrabalhoService.atualizaPrograma(id, dados);
        return ResponseEntity.ok(new RetornaProgramaDeTrabalhoDTO(programaAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarProgramaDeTrabalho(@PathVariable Long id) {
        programaDeTrabalhoService.deletaPrograma(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Programa de trabalho com id " + id + " excluido!"));
    }

}
