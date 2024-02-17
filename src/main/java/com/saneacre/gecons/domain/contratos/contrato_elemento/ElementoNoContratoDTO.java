package com.saneacre.gecons.domain.contratos.contrato_elemento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ElementoNoContratoDTO(@NotNull @Positive(message = "ID inválido!")
                                    Long idContrato,
                                    @NotNull @Positive(message = "ID inválido!")
                                    Long idElemento) {
}
