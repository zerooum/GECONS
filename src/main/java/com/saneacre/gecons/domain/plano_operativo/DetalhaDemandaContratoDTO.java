package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import java.math.BigDecimal;

public record DetalhaDemandaContratoDTO(Long idContrato, String numeroContrato, BigDecimal quantConsumo,
                                        BigDecimal quantRegistro, BigDecimal valorUnitario) {

    public DetalhaDemandaContratoDTO(ContratoFornecedorPoEntity dados) {
        this(dados.getContrato().getId(), dados.getContrato().getNumero(), dados.getQuantConsumo(),
                dados.getQuantRegistro(), dados.getValorUnitario());
    };
}
