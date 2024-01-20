package com.saneacre.gecons.domain.contrato_fornecedor_po;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ItemNoContratoDTO(@NotNull @NotBlank
                                String contrato,
                                @NotNull @NotBlank
                                String fornecedor,
                                @NotNull @NotBlank
                                String demanda,
                                @PositiveOrZero(message = "Quantidade de consumo deve ser maior ou igual a zero")
                                BigDecimal quant_consumo,
                                @Positive(message = "Quantidade de registro deve ser maior que zero")
                                BigDecimal quant_registro,
                                @Positive(message = "Valor unitário deve ser maior que zero")
                                BigDecimal valor_unitario) {
}
