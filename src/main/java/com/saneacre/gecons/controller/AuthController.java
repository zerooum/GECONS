package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.usuario.AutenticacaoDTO;
import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import com.saneacre.gecons.infra.security.TokenJWTDTO;
import com.saneacre.gecons.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authToken);
        var jwtToken = tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(jwtToken));
    }
}
