package com.saneacre.gecons.domain.contratos.contrato_elemento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ElementoNoContratoDTO(@NotNull @NotBlank
                                    String contrato,
                                    @NotNull @NotBlank
                                    String elemento) {
}
