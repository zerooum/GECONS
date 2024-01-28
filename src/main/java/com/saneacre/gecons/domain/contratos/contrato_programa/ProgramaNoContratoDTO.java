package com.saneacre.gecons.domain.contratos.contrato_programa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProgramaNoContratoDTO(@NotNull @NotBlank
                                    String contrato,
                                    @NotNull @NotBlank
                                    String programa) {
}
