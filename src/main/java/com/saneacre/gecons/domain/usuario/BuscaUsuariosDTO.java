package com.saneacre.gecons.domain.usuario;

public record BuscaUsuariosDTO(Long id, String login, String role) {

    public BuscaUsuariosDTO(UsuarioEntity usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }
}
