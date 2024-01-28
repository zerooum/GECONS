package com.saneacre.gecons.domain.contratos.elemento_de_despesa;

public record RetornaElementoDeDespesaDTO(Long id, String numero, String descricao) {

    public RetornaElementoDeDespesaDTO(ElementoDeDespesaEntity dados) {
        this(dados.getId(), dados.getNumero(), dados.getDescricao());
    }
}
