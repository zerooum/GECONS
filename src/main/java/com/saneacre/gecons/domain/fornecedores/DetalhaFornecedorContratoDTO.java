package com.saneacre.gecons.domain.fornecedores;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;

import java.math.BigDecimal;

public record DetalhaFornecedorContratoDTO(Long idContrato, String numeroContrato, String objetoContrato,
                                           BigDecimal valorContrato) {

    public DetalhaFornecedorContratoDTO(ContratoFornecedorPoEntity dados) {
        this(dados.getContrato().getId(), dados.getContrato().getNumero(), dados.getContrato().getObjeto(),
                dados.getContrato().getValor());
    }
}
