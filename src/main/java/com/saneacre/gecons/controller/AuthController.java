package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.autenticacao_registro.AuthService;
import com.saneacre.gecons.domain.usuario.*;
import com.saneacre.gecons.domain.autenticacao_registro.AutenticacaoDTO;
import com.saneacre.gecons.domain.autenticacao_registro.RegistroDTO;
import com.saneacre.gecons.infra.security.TokenJWTDTO;
import com.saneacre.gecons.infra.security.TokenService;
import com.saneacre.gecons.utils.RespostaSimplesDTO;
import jakarta.validation.Valid;
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
    private AuthService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenJWTDTO> efetuarLogin(@RequestBody @Valid AutenticacaoDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authToken);
        var jwtToken = tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJWTDTO(dados.login(), jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<RespostaSimplesDTO> registraUsuario(@RequestBody @Valid RegistroDTO dados) {
        service.registraUsuario(dados);
        return ResponseEntity.ok(new RespostaSimplesDTO("Usuário criado com sucesso!"));
    }
}
