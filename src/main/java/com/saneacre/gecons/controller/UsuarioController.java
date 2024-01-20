package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.usuario.RetornaUsuarioDTO;
import com.saneacre.gecons.domain.usuario.UsuarioService;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.RetornaPermissoesDTO;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoDTO;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.BuscaSistemasDTO;
import com.saneacre.gecons.utils.RespostaSimplesDTO;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioSistemaPermissaoRepository usuarioSistemaPermissaoRepository;

    @GetMapping
    public ResponseEntity<Page<RetornaUsuarioDTO>> listarUsuarios(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var usuariosPage = service.buscarTodosUsuarios(paginacao);
        return ResponseEntity.ok(usuariosPage);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<RespostaSimplesDTO> deletaUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.ok().body(new RespostaSimplesDTO("Usuario com id " + id + " excluido!"));
    }

    @GetMapping("/sistemas")
    public ResponseEntity<BuscaSistemasDTO> recuperaSistemasUsuario(@RequestBody @Valid BuscaSistemasDTO dados) {
        var sistemas = service.getSistemasUsuarios(dados.usuario());
        return ResponseEntity.ok(new BuscaSistemasDTO(dados.usuario(), sistemas));
    }

    @GetMapping("/permissoes")
    public ResponseEntity<RetornaPermissoesDTO> recuperaSistemasUsuario(@RequestBody @Valid RetornaPermissoesDTO dados) {
        var permissoes = service.getPermissoesUsuarios(dados.usuario());
        return ResponseEntity.ok(new RetornaPermissoesDTO(dados.usuario(), permissoes));
    }

    @PostMapping("/permissoes")
    @Transactional
    public ResponseEntity<RespostaSimplesDTO> adicionaPermissao(@RequestBody @Valid PermissaoDTO dados) {
        var usuarioSistemaPermissao = service.criaUsuarioSistemaPermissao(dados);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespostaSimplesDTO("Permissão " + dados.permissao() + " concedida ao usuário "
                                              + dados.usuario() + " no sistema " + dados.sistema()));
    }

    @DeleteMapping("/permissoes")
    @Transactional
    public ResponseEntity<RespostaSimplesDTO> removePermissao(@RequestBody @Valid PermissaoDTO dados) {
        service.deletaUsuarioSistemaPermissao(dados);
        return ResponseEntity.ok()
                .body(new RespostaSimplesDTO("Permissão " + dados.permissao() + " no sistema " + dados.sistema()
                                                + " retirada do usuário " + dados.usuario()));
    }

}
