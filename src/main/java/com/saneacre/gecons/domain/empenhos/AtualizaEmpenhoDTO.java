package com.saneacre.gecons.domain.empenhos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizaEmpenhoDTO(@Pattern(regexp = "^(?!\\s*$).+", message = "O numero do empenho não pode estar em branco!")
                                 String numero,
                                 @Positive(message = "O valor do empenho deve ser maior que zero!")
                                 BigDecimal valor,
                                 LocalDate data,
                                 String descricao) {
}
