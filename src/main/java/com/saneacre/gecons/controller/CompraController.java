package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.compras.CompraService;
import com.saneacre.gecons.domain.compras.CriaCompraDTO;
import com.saneacre.gecons.domain.compras.RetornaCompraDTO;
import com.saneacre.gecons.domain.contratos.CriaContratoDTO;
import com.saneacre.gecons.domain.contratos.RetornaContratoDTO;
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
@RequestMapping("/compras")
@PreAuthorize("hasRole('COMPRAS') or hasRole('ADMIN')")
public class CompraController {

    @Autowired
    private CompraService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('COMPRAS_INSERIR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaCompraDTO> cadastraCompra(@RequestBody @Valid CriaCompraDTO dados,
                                                             UriComponentsBuilder uriBuilder) {
        var compraCriada = service.cadastrarCompra(dados);
        var uri = uriBuilder.path("/compras/{id}").buildAndExpand(compraCriada.idCompra()).toUri();
        return ResponseEntity.created(uri).body(compraCriada);
    }

    @GetMapping
    @PreAuthorize("hasRole('COMPRAS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RetornaCompraDTO>> listarCompras(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var pageCompras = service.buscaTodasCompras(paginacao);
        return ResponseEntity.ok(pageCompras);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COMPRAS_VISUALIZAR') or hasRole('ADMIN')")
    public ResponseEntity<RetornaCompraDTO> buscaCompra(@PathVariable Long id) {
        var compra = service.buscaCompraPorId(id);
        return ResponseEntity.ok(compra);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('COMPRAS_DELETAR') or hasRole('ADMIN')")
    public ResponseEntity<RespostaSimplesDTO> deletarCompra(@PathVariable Long id) {
        service.deletaCompra(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Compra com id " + id + " excluida!"));
    }
}
