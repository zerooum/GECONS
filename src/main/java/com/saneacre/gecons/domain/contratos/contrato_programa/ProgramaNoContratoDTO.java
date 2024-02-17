package com.saneacre.gecons.domain.contratos.contrato_programa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProgramaNoContratoDTO(@NotNull @Positive(message = "ID inválido!")
                                    Long idContrato,
                                    @NotNull @Positive(message = "ID inválido!")
                                    Long idPrograma) {
}
