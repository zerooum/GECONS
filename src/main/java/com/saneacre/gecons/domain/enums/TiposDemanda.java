package com.saneacre.gecons.domain.enums;

public enum TiposDemanda {
    INSUMO("Insumo"),
    SERVICO("Serviço");

    private String descricao;

    TiposDemanda(String descricao) {
        this.descricao = descricao;
    }

    public static TiposDemanda fromString(String texto) {
        for (TiposDemanda demanda : TiposDemanda.values()) {
            if (demanda.descricao.equalsIgnoreCase(texto)) {
                return demanda;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de demanda com esse nome!");
    }

    public String getDescricao() {
        return descricao;
    }
}