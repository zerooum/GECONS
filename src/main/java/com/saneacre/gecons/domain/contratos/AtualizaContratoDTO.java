package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizaContratoDTO(

        TiposContrato tipo,
        @Size(min = 1, message = "O numero do contrato não pode estar em branco!")
        String numero,
        @Size(min = 10, message = "O objeto não pode estar em branco e precisa ter pelo menos 10 caracteres")
        String objeto,
        LocalDate dataAssinatura,
        @Positive(message = "O valor do contrato precisa ser positivo!")
        BigDecimal valor,
        LocalDate dataVigencia,
        String numeroSei,
        String portaria,
        String fiscalTitular,
        String fiscalSubstituto,
        String gestorTitular,
        String gestorSubstituto
) {
}
