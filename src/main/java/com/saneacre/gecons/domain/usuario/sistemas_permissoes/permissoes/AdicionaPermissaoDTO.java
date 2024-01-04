package com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdicionaPermissaoDTO(

        @NotNull @NotBlank
        String usuario,
        @NotNull @NotBlank
        String sistema,
        @NotNull @NotBlank
        String permissao) {
}
