package com.saneacre.gecons.domain.enums;

public enum Unidades {
    GRAMA("g"),
    LITRO("l"),
    METRO("m"),
    METRO2("m²"),
    METRO3("m³"),
    MILIGRAMA("mg"),
    QUILOGRAMAS("kg"),
    TONELADAS("ton"),
    UNIDADE("und");

    private String descricao;

    Unidades(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
