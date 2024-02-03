package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;

import java.math.BigDecimal;

public record EmpenhosContratoDTO(String numeroEmpenho, String programa, String elemento, String fonte, BigDecimal valor) {

    public EmpenhosContratoDTO(EmpenhoEntity dados) {
        this(dados.getNumero(), dados.getPrograma().getNumero(), dados.getElemento().getNumero(),
                dados.getFonte().getNumero(), dados.getValor());
    }
}
