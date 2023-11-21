package com.saneacre.gecons.domain.enums;

public enum TiposContrato {
    ATA("Ata"),
    CONTRATO("Contrato");

    private String descricao;

    TiposContrato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}