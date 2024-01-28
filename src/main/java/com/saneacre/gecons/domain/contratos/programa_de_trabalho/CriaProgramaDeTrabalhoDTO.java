package com.saneacre.gecons.domain.contratos.programa_de_trabalho;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriaProgramaDeTrabalhoDTO(@NotNull @NotBlank
                                        String numero,
                                        String descricao) {
}
