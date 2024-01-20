package com.saneacre.gecons.domain.usuario;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record RetornaUsuarioDTO(Long id, String login, String role) {

    public RetornaUsuarioDTO(UsuarioEntity usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }
}
