package com.saneacre.gecons.domain.contratos.contrato_fonte;

public record FontesContratoDTO(String numeroFonte, String descricaoFonte) {

    public FontesContratoDTO(ContratoFonteEntity dados) {
        this(dados.getFonte().getNumero(), dados.getFonte().getDescricao());
    }
}
