package com.saneacre.gecons.domain.programa_de_trabalho;

import jakarta.validation.constraints.Pattern;

public record AtualizaProgramaDeTrabalhoDTO(@Pattern(regexp = "^(?!\\s*$).+",
                                                    message = "O numero do programa de trabalho não pode estar em branco!")
                                            String numero,
                                            String descricao) {
}
