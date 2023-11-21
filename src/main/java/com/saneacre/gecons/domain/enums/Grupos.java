package com.saneacre.gecons.domain.enums;

public enum Grupos {
    ADMINISTRATIVO("Administrativo"),
    ALUGUEL("Aluguel"),
    CONSTRUÇÃO("Construção"),
    ELETRICO("Elétrico"),
    HIDRAULICO("Hidráulico"),
    TRANSPORTE("Transporte");

    private String descricao;

    Grupos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}