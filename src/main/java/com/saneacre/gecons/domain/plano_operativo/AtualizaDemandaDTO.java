package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AtualizaDemandaDTO(

        @Pattern(regexp = "^(?!\\s*$).+", message = "O nome do item não pode estar em branco!")
        String nome,
        Unidades unidade,
        TiposDemanda tipo,
        Grupos grupo,
        @PositiveOrZero(message = "A quantidade precisa ser zero ou positiva!")
        BigDecimal quantidade) {
}
