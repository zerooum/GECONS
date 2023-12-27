package com.saneacre.gecons.domain.enums;

public enum Grupos {
    ADMINISTRATIVO("Administrativo"),
    ALUGUEL("Aluguel"),
    CONSTRUCAO("Construção"),
    ELETRICO("Elétrico"),
    HIDRAULICO("Hidráulico"),
    TRANSPORTE("Transporte");

    private String descricao;

    Grupos(String descricao) {
        this.descricao = descricao;
    }

    public static Grupos fromString(String texto) {
        for (Grupos grupo : Grupos.values()) {
            if (grupo.descricao.equalsIgnoreCase(texto)) {
                return grupo;
            }
        }
        throw new IllegalArgumentException("Nenhum grupo com esse nome!");
    }


    public String getDescricao() {
        return descricao;
    }


}