package com.saneacre.gecons.domain.demanda;

import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record BuscaDemandasDTO(@NotNull @NotBlank
                               Long id,
                               @NotBlank
                               String nome,
                               Unidades unidade,
                               TiposDemanda tipo,
                               Grupos grupo,
                               @PositiveOrZero
                               BigDecimal quantidade) {

    public BuscaDemandasDTO(DemandaEntity demanda){
        this(demanda.getId(), demanda.getNome(), demanda.getUnidade(), demanda.getTipo(), demanda.getGrupo(),
                demanda.getQuantidade());
    }

}
