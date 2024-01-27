package com.saneacre.gecons.domain.elemento_de_despesa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriaElementoDeDespesaDTO(@NotNull @NotBlank
                                       String numero,
                                       String descricao) {
}
