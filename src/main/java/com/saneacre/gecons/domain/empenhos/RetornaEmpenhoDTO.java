package com.saneacre.gecons.domain.empenhos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RetornaEmpenhoDTO(Long id,
                                Long idContrato,
                                Long idPrograma,
                                Long idElemento,
                                Long idFonte,
                                String numero,
                                BigDecimal valor,
                                LocalDate data,
                                String descricao) {

    public RetornaEmpenhoDTO(EmpenhoEntity dados) {
        this(dados.getId(), dados.getContrato().getId(), dados.getPrograma().getId(), dados.getElemento().getId(),
                dados.getFonte().getId(), dados.getNumero(), dados.getValor(), dados.getData(), dados.getDescricao());
    }
}
