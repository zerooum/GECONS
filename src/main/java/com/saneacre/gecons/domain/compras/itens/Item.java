package com.saneacre.gecons.domain.compras.itens;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record Item(@NotNull @Positive(message = "ID de fornecedor inválido!")
                   Long idFornecedor,
                   @NotNull @Positive(message = "ID de demanda inválido!")
                   Long idDemanda,
                   @Positive(message = "A quantidade do item deve ser maior que zero!")
                   BigDecimal quantidade) {
}
