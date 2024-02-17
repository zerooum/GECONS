package com.saneacre.gecons.domain.contrato_fornecedor_po;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ItemNoContratoDTO(@NotNull @Positive(message = "ID inválido!")
                                Long idContrato,
                                @NotNull @Positive(message = "ID inválido!")
                                Long idFornecedor,
                                @NotNull @Positive(message = "ID inválido!")
                                Long idDemanda,
                                @PositiveOrZero(message = "Quantidade de consumo deve ser maior ou igual a zero")
                                BigDecimal quant_consumo,
                                @Positive(message = "Quantidade de registro deve ser maior que zero")
                                BigDecimal quant_registro,
                                @Positive(message = "Valor unitário deve ser maior que zero")
                                BigDecimal valor_unitario) {
}
