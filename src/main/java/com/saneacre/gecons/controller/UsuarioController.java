package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.usuario.BuscaUsuariosDTO;
import com.saneacre.gecons.domain.usuario.UsuarioRepository;
import com.saneacre.gecons.domain.usuario.UsuarioService;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.BuscaPermissoesDTO;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.ConcedePermissaoDTO;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.BuscaSistemasDTO;
import com.saneacre.gecons.infra.erros.UsuarioJaAdminException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioSistemaPermissaoRepository usuarioSistemaPermissaoRepository;

    @GetMapping
    public ResponseEntity<Page<BuscaUsuariosDTO>> listarUsuarios(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page = usuarioRepository.findAll(paginacao).map(BuscaUsuariosDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/sistemas")
    public ResponseEntity<BuscaSistemasDTO> recuperaSistemasUsuario(@RequestBody @Valid BuscaSistemasDTO dados) {
        var sistemas = service.getSistemasUsuarios(dados.usuario());
        var resposta = new BuscaSistemasDTO(dados.usuario(), sistemas);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/permissoes")
    public ResponseEntity<BuscaPermissoesDTO> recuperaSistemasUsuario(@RequestBody @Valid BuscaPermissoesDTO dados) {
        var permissoes = service.getPermissoesUsuarios(dados.usuario());
        var resposta = new BuscaPermissoesDTO(dados.usuario(), permissoes);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/permissoes")
    @Transactional
    public ResponseEntity adicionaPermissao(@RequestBody @Valid ConcedePermissaoDTO dados) {
        var usuarioSistemaPermissao = service.getUsuarioSistemaPermissao(dados.usuario(), dados.sistema(), dados.permissao());
        if (Objects.equals(usuarioSistemaPermissao.getUsuario().getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        usuarioSistemaPermissaoRepository.save(usuarioSistemaPermissao);
        var resposta = Map.of("Usuario", usuarioSistemaPermissao.getUsuario().getLogin(),
                              "Sistema", usuarioSistemaPermissao.getSistema().getNome(),
                              "Permissao", usuarioSistemaPermissao.getPermissao().getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/permissoes")
    @Transactional
    public ResponseEntity removePermissao(@RequestBody @Valid ConcedePermissaoDTO dados) {
        var usuarioSistemaPermissao = service.getUsuarioSistemaPermissao(dados.usuario(), dados.sistema(), dados.permissao());
        usuarioSistemaPermissaoRepository.delete(usuarioSistemaPermissao);
        return ResponseEntity.noContent().build();
    }


}
