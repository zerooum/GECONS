package com.saneacre.gecons.domain.usuario.auth_registro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutenticacaoDTO(@NotNull @NotBlank(message = "Por favor informar um usuário para login")
                              String login,
                              @NotNull @NotBlank(message = "Por favor informar uma senha")
                              String senha) {
}
