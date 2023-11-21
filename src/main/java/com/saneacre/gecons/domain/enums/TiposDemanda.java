package com.saneacre.gecons.domain.enums;

public enum TiposDemanda {
    INSUMO("Insumo"),
    SERVIÇO("Serviço");

    private String descricao;

    TiposDemanda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}