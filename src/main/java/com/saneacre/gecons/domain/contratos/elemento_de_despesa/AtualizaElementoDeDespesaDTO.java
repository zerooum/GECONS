package com.saneacre.gecons.domain.contratos.elemento_de_despesa;

import jakarta.validation.constraints.Pattern;

public record AtualizaElementoDeDespesaDTO(@Pattern(regexp = "^(?!\\s*$).+",
                                                message = "O numero do elemento de despesa não pode estar em branco!")
                                           String numero,
                                           String descricao) {
}
