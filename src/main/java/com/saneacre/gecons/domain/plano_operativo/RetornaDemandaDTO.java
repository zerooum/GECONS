package com.saneacre.gecons.domain.plano_operativo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RetornaDemandaDTO(Long id,
                                String nome,
                                String unidade,
                                String tipo,
                                String grupo,
                                BigDecimal quantidade) {

    public RetornaDemandaDTO(DemandaEntity demanda){
        this(demanda.getId(), demanda.getNome(), demanda.getUnidade().getDescricao(), demanda.getTipo().getDescricao(),
                demanda.getGrupo().getDescricao(), demanda.getQuantidade());
    }


}
