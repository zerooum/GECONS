package com.saneacre.gecons.domain.contratos.contrato_fonte;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContratoFonteId {

    @Column(name = "id_contrato")
    private Long IdContrato;

    @Column(name = "id_fonte")
    private Long IdFonte;
}
