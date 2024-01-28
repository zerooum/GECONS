package com.saneacre.gecons.domain.contratos.contrato_programa;

public record ProgramasContratoDTO(String numeroPrograma, String descricaoPrograma) {

    public ProgramasContratoDTO(ContratoProgramaEntity dados) {
        this(dados.getPrograma().getNumero(), dados.getPrograma().getDescricao());

    }
}
