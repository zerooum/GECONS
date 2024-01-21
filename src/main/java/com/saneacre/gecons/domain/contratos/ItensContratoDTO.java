package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.enums.Unidades;

import java.math.BigDecimal;

public record ItensContratoDTO(String nomeFornecedor, String nomeDemanda, Unidades unidadeDemanda, BigDecimal quantConsumo,
                               BigDecimal quantRegistro, BigDecimal valorUnitario) {

    public ItensContratoDTO(ContratoFornecedorPoEntity dados) {
        this(dados.getFornecedor().getNome(), dados.getDemanda().getNome(), dados.getDemanda().getUnidade(),
                dados.getQuantConsumo(), dados.getQuantRegistro(), dados.getValorUnitario());
    }
}
