package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.usuario.*;
import com.saneacre.gecons.infra.security.TokenJWTDTO;
import com.saneacre.gecons.infra.security.TokenService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authToken);
        var jwtToken = tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity registraUsuario(@RequestBody @Valid RegistroDTO dados) {
        if(repository.findByLogin(dados.login()) != null) return ResponseEntity.badRequest().build();
        boolean primeiroUsuario = repository.count() == 0;
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        System.out.println(senhaCriptografada);
        UsuarioEntity novoUsuario;

        if (primeiroUsuario) {
            novoUsuario = new UsuarioEntity(dados.login(), senhaCriptografada, UsuarioRoles.ADMIN);
        } else {
            novoUsuario = new UsuarioEntity(dados.login(), senhaCriptografada, UsuarioRoles.USUARIO);
        }

        this.repository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
