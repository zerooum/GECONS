package com.saneacre.gecons.domain.contratos.fontes;

public record RetornaFonteDTO(Long id, String numero, String descricao) {

    public RetornaFonteDTO(FonteEntity dados) {
        this(dados.getId(), dados.getNumero(), dados.getDescricao());
    }
}
