package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contratos.elemento_de_despesa.AtualizaElementoDeDespesaDTO;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.CriaElementoDeDespesaDTO;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaService;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.RetornaElementoDeDespesaDTO;
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
@RequestMapping("/elementos-de-despesa")
@PreAuthorize("hasRole('CONTRATOS') or hasRole('ADMIN')")
public class ElementoDeDespesaController {

    @Autowired
    ElementoDeDespesaService elementoDeDespesaService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaElementoDeDespesaDTO> criaElementoDeDespesa(@RequestBody @Valid CriaElementoDeDespesaDTO dados,
                                                                                    UriComponentsBuilder uriBuilder) {
        var elementoCriado = elementoDeDespesaService.criaElementoDeDespesa(dados);
        var uri = uriBuilder.path("/elementos-de-despesa/{id}").buildAndExpand(elementoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaElementoDeDespesaDTO(elementoCriado));
    }

    @GetMapping
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaElementoDeDespesaDTO>> listaElementoDeDespesa(@PageableDefault(size = 10, sort = {"id"})
                                                                                       Pageable paginacao) {
        var pageElementos = elementoDeDespesaService.buscarTodosElementos(paginacao);
        return ResponseEntity.ok(pageElementos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaElementoDeDespesaDTO> detalhaElementoDeDespesa(@PathVariable Long id) {
        var elemento = elementoDeDespesaService.buscaElementoPorId(id);
        return ResponseEntity.ok(elemento);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaElementoDeDespesaDTO> atualizaElementoDeDespesa(@RequestBody @Valid AtualizaElementoDeDespesaDTO dados,
                                                                                   @PathVariable Long id) {
        var elementoAtualizado = elementoDeDespesaService.atualizaElemento(id, dados);
        return ResponseEntity.ok(new RetornaElementoDeDespesaDTO(elementoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarElementoDeDespesa(@PathVariable Long id) {
        elementoDeDespesaService.deletaElemento(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Elemento de despesa com id " + id + " excluido!"));
    }

}
