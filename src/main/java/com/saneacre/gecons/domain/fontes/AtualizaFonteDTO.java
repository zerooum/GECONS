package com.saneacre.gecons.domain.fontes;

import jakarta.validation.constraints.Pattern;

public record AtualizaFonteDTO(@Pattern(regexp = "^(?!\\s*$).+",
                                    message = "O numero da fonte não pode estar em branco!")
                               String numero,
                               String descricao) {
}
