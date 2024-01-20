package com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissaoDTO(

        @NotNull @NotBlank
        String usuario,
        @NotNull @NotBlank
        String sistema,
        @NotNull @NotBlank
        String permissao) {
}
