package com.saneacre.gecons.domain.contratos.contrato_fonte;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FonteNoContratoDTO(@NotNull @Positive(message = "ID inválido!")
                                 Long idContrato,
                                 @NotNull @Positive(message = "ID inválido!")
                                 Long idFonte) {
}
