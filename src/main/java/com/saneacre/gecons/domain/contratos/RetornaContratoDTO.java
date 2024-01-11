package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RetornaContratoDTO(

        Long id,
        TiposContrato tipo,
        String numero,
        String objeto,
        LocalDate dataAssinatura,
        BigDecimal valor,
        LocalDate dataVigencia,
        String numeroSei,
        String portaria,
        String fiscalTitular,
        String fiscalSubstituto,
        String gestorTitular,
        String gestorSubstituto

) {

    public RetornaContratoDTO(ContratoEntity contrato) {
        this(contrato.getId(), contrato.getTipo(), contrato.getNumero(), contrato.getObjeto(),
                contrato.getDataAssinatura(), contrato.getValor(), contrato.getDataVigencia(), contrato.getNumeroSei(),
                contrato.getPortaria(), contrato.getFiscalTitular(), contrato.getFiscalSubstituto(),
                contrato.getFiscalTitular(), contrato.getGestorSubstituto());
    }
}
