package com.saneacre.gecons.domain.enums;

public enum PersonalidadeJuridica {
    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private String descricao;

    PersonalidadeJuridica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
