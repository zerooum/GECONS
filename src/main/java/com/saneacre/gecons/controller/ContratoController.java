package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ItemNoContratoDTO;
import com.saneacre.gecons.domain.contratos.*;
import com.saneacre.gecons.utils.RespostaSimplesDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CONTRATOS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaContratoDTO> buscaContrato(@PathVariable Long id) {
        var contrato = service.buscaContratoPorId(id);
        return ResponseEntity.ok(contrato);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_ATUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaContratoDTO> atualizaContrato(@RequestBody @Valid AtualizaContratoDTO dados, @PathVariable Long id){
        ContratoEntity contratoAtualizado = service.atualizarContrato(id, dados);
        return ResponseEntity.ok(new RetornaContratoDTO(contratoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarContrato(@PathVariable Long id) {
        service.deletaContrato(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Contrato com id " + id + " excluido!"));
    }

    @PreAuthorize("hasRole('CONTRATOS_INSERIR') or hasRole('ADMIN')")
    @PostMapping("/item")
    @Transactional
    public ResponseEntity<ItemNoContratoDTO> adicionaItemNoContrato(@RequestBody @Valid ItemNoContratoDTO dados) {
        var contratoFornecedorPo = service.adicionaItemNoContrato(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    @PreAuthorize("hasRole('CONTRATOS_DELETAR') or hasRole('ADMIN')")
    @DeleteMapping("/item")
    @Transactional
    public ResponseEntity<RespostaSimplesDTO> removeItemNoContrato(@RequestBody @Valid ItemNoContratoDTO dados) {
        service.removeItemNoContrato(dados);
        return ResponseEntity.ok()
                .body(new RespostaSimplesDTO("Item " + dados.demanda() + " removido do contrato " + dados.contrato()
                        + " para o fornecedor " + dados.fornecedor()));
    }






}
