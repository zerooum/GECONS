package com.saneacre.gecons.domain.empenhos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriaEmpenhoDTO(@Positive(message = "ID de contrato inválido!")
                             Long idContrato,
                             @Positive(message = "ID de programa inválido!")
                             Long idPrograma,
                             @Positive(message = "ID de elemento inválido!")
                             Long idElemento,
                             @Positive(message = "ID de fonte inválido!")
                             Long idFonte,
                             @NotNull @NotBlank
                             String numero,
                             @Positive(message = "O valor do empenho deve ser maior que zero!")
                             BigDecimal valor,
                             @NotNull(message = "A data do empenho deve ser informada!")
                             LocalDate data,
                             String descricao) {

}
