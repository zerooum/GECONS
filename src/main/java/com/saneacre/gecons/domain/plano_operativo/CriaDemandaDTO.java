package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CriaDemandaDTO(

        @NotNull @NotBlank
        String nome,
        @NotNull
        Unidades unidade,
        @NotNull
        TiposDemanda tipo,
        @NotNull
        Grupos grupo,
        @PositiveOrZero
        BigDecimal quantidade) {
}
