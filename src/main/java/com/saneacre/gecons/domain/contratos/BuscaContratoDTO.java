package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public record BuscaContratoDTO(

        Long id,
        TiposContrato tipo,
        @NotBlank
        String numero,
        @NotBlank
        String objeto,
        Date dataAssinatura,
        @Positive
        BigDecimal valor,
        Date dataVigencia,
        String numeroSei,
        String portaria,
        String fiscalTitular,
        String fiscalSubstituto,
        String gestorTitular,
        String gestorSubstituto
) {

    public BuscaContratoDTO(ContratoEntity contrato) {
        this(contrato.getId(), contrato.getTipo(), contrato.getNumero(), contrato.getObjeto(),
                contrato.getDataAssinatura(), contrato.getValor(), contrato.getDataVigencia(), contrato.getNumeroSei(),
                contrato.getPortaria(), contrato.getFiscalTitular(), contrato.getFiscalSubstituto(),
                contrato.getFiscalTitular(), contrato.getGestorSubstituto());
    }
}
