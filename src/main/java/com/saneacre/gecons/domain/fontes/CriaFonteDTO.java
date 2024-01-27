package com.saneacre.gecons.domain.fontes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriaFonteDTO(@NotNull @NotBlank
                           String numero,
                           String descricao) {

}
