package com.saneacre.gecons.domain.enums;

public enum PersonalidadeJuridica {
    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private String descricao;

    PersonalidadeJuridica(String descricao) {
        this.descricao = descricao;
    }

    public static PersonalidadeJuridica fromString(String texto) {
        for (PersonalidadeJuridica pj : PersonalidadeJuridica.values()) {
            if (pj.descricao.equalsIgnoreCase(texto)) {
                return pj;
            }
        }
        throw new IllegalArgumentException("Nenhuma personalidade juridica com esse nome!");
    }

    public String getDescricao() {
        return descricao;
    }
}
