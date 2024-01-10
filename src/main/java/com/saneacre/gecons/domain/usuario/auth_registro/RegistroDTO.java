package com.saneacre.gecons.domain.usuario.auth_registro;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroDTO(@NotNull @NotBlank(message = "Por favor informar um usuário para cadastro")
                          @Size(min = 6, message = "O usuário deve conter pelo menos 6 caracteres")
                          String login,
                          @NotNull @NotBlank(message = "Por favor informar uma senha para cadastro")
                          @Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres")
                          String senha) {
}
