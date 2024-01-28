package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contratos.fontes.AtualizaFonteDTO;
import com.saneacre.gecons.domain.contratos.fontes.CriaFonteDTO;
import com.saneacre.gecons.domain.contratos.fontes.FonteService;
import com.saneacre.gecons.domain.contratos.fontes.RetornaFonteDTO;
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
@RequestMapping("/fontes")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class FonteController {

    @Autowired
    FonteService fonteService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFonteDTO> criaFonte(@RequestBody @Valid CriaFonteDTO dados,
                                                                      UriComponentsBuilder uriBuilder) {
        var fonteCriada = fonteService.criaFonte(dados);
        var uri = uriBuilder.path("/fontes/{id}").buildAndExpand(fonteCriada.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaFonteDTO(fonteCriada));
    }

    @GetMapping
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaFonteDTO>> listaFonte(@PageableDefault(size = 10, sort = {"id"})
                                                                                       Pageable paginacao) {
        var pageFontes = fonteService.buscarTodasFontes(paginacao);
        return ResponseEntity.ok(pageFontes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFonteDTO> detalhaFonte(@PathVariable Long id) {
        var fonte = fonteService.buscaFontePorId(id);
        return ResponseEntity.ok(fonte);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaFonteDTO> atualizaFonte(@RequestBody @Valid AtualizaFonteDTO dados,
                                                         @PathVariable Long id) {
        var fonteAtualizada = fonteService.atualizaFonte(id, dados);
        return ResponseEntity.ok(new RetornaFonteDTO(fonteAtualizada));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarFonte(@PathVariable Long id) {
        fonteService.deletaFonte(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Fonte com id " + id + " excluido!"));
    }
}
