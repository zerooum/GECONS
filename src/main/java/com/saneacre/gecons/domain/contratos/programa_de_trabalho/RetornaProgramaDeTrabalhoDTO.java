package com.saneacre.gecons.domain.contratos.programa_de_trabalho;

public record RetornaProgramaDeTrabalhoDTO(Long id, String numero, String descricao) {

    public RetornaProgramaDeTrabalhoDTO(ProgramaDeTrabalhoEntity dados) {
        this(dados.getId(), dados.getNumero(), dados.getDescricao());
    }
}
