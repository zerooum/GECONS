package com.saneacre.gecons.domain.enums;

public enum TiposContrato {
    ATA("Ata"),
    CONTRATO("Contrato");

    private String descricao;

    TiposContrato(String descricao) {
        this.descricao = descricao;
    }

    public static TiposContrato fromString(String texto) {
        for (TiposContrato contrato : TiposContrato.values()) {
            if (contrato.descricao.equalsIgnoreCase(texto)) {
                return contrato;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de contrato com esse nome!");
    }

    public String getDescricao() {
        return descricao;
    }
}