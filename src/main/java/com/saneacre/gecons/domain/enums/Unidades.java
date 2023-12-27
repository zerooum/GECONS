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

    public static Unidades fromString(String texto) {
        for (Unidades unidade : Unidades.values()) {
            if (unidade.descricao.equalsIgnoreCase(texto)) {
                return unidade;
            }
        }
        throw new IllegalArgumentException("Nenhuma unidade com esse nome!");
    }

    public String getDescricao() {
        return descricao;
    }
}
