package com.saneacre.gecons.domain.contratos.contrato_elemento;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContratoElementoId {

    @Column(name = "id_contrato")
    private Long IdContrato;

    @Column(name = "id_elemento")
    private Long IdElemento;

}
