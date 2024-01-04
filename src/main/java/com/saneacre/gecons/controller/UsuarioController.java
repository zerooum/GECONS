package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.usuario.UsuarioService;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.AdicionaPermissaoDTO;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioSistemaPermissaoRepository usuarioSistemaPermissaoRepository;

    @PostMapping("/permissoes")
    @Transactional
    public ResponseEntity adicionaPermissao(@Valid @RequestBody AdicionaPermissaoDTO dados) {
        var usuarioSistemaPermissao = service.getUsuarioSistemaPermissao(dados.usuario(), dados.sistema(), dados.permissao());
        usuarioSistemaPermissaoRepository.save(usuarioSistemaPermissao);
        return ResponseEntity.ok(usuarioSistemaPermissao);
    }
}
