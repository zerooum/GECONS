package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriaContratoDTO(

        @NotNull
        TiposContrato tipo,
        @NotNull @NotBlank
        String numero,
        @NotNull @NotBlank
        @Size(min = 10, message = "O objeto não pode estar em branco e precisa ter pelo menos 10 caracteres")
        String objeto,
        @NotNull
        LocalDate dataAssinatura,
        @Positive
        BigDecimal valor,
        @NotNull
        LocalDate dataVigencia,
        String numeroSei,
        String portaria,
        String fiscalTitular,
        String fiscalSubstituto,
        String gestorTitular,
        String gestorSubstituto
        ) {
}
