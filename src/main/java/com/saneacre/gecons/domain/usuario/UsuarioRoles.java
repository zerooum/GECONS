package com.saneacre.gecons.domain.usuario;

import com.saneacre.gecons.domain.enums.PersonalidadeJuridica;

public enum UsuarioRoles {
    ADMIN("Admin"),
    USUARIO("Usuário");

    private String role;

    UsuarioRoles(String role) {
        this.role = role;
    }

    public static UsuarioRoles fromString(String texto) {
        for (UsuarioRoles r : UsuarioRoles.values()) {
            if (r.role.equalsIgnoreCase(texto)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Nenhuma personalidade juridica com esse nome!");
    }

    public String getRole() {
        return role;
    }

}
