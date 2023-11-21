package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public record CriaContratoDTO(

        @NotNull
        TiposContrato tipo,
        @NotNull @NotBlank
        String numero,
        @NotNull @NotBlank
        String objeto,
        @NotNull
        Date dataAssinatura,
        @Positive
        BigDecimal valor,
        @NotNull
        Date dataVigencia,
        String numeroSei,
        String portaria,
        String fiscalTitular,
        String fiscalSubstituto,
        String gestorTitular,
        String gestorSubstituto
        ) {
}
