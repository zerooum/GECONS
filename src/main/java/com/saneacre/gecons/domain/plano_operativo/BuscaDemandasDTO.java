package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record BuscaDemandasDTO(Long id,
                               @NotBlank
                               String nome,
                               String unidade,
                               String tipo,
                               String grupo,
                               @PositiveOrZero
                               BigDecimal quantidade) {

    public BuscaDemandasDTO(DemandaEntity demanda){
        this(demanda.getId(), demanda.getNome(), demanda.getUnidade().getDescricao(), demanda.getTipo().getDescricao(),
                demanda.getGrupo().getDescricao(), demanda.getQuantidade());
    }


}
