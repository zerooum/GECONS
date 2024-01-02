package com.saneacre.gecons.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioEntity usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("GeconsAPI")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String recuperarToken(HttpServletRequest req) {
        var authHeader = req.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ","").trim();
        }
        return null;
    }

    public String getTokenSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("GeconsAPI")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token jwt inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
        var tempoExpiracao = 2;
        var fusoHorario = "-05:00";
        return LocalDateTime.now().plusHours(tempoExpiracao).toInstant(ZoneOffset.of(fusoHorario));
    }
}
