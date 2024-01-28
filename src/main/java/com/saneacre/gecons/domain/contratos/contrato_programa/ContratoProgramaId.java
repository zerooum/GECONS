package com.saneacre.gecons.domain.contratos.contrato_programa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContratoProgramaId {

    @Column(name = "id_contrato")
    private Long IdContrato;

    @Column(name = "id_programa")
    private Long IdPrograma;

}
