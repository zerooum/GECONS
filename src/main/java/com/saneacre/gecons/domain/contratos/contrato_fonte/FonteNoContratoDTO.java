package com.saneacre.gecons.domain.contratos.contrato_fonte;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FonteNoContratoDTO(@NotNull @NotBlank
                                 String contrato,
                                 @NotNull @NotBlank
                                 String fonte) {
}
