package com.saneacre.gecons.domain.contratos.contrato_elemento;

public record ElementosContratoDTO(String numeroElemento, String descricaoElemento) {

    public ElementosContratoDTO(ContratoElementoEntity dados) {
        this(dados.getElemento().getNumero(), dados.getElemento().getDescricao());
    }
}
