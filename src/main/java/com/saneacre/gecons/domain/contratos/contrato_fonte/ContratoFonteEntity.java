package com.saneacre.gecons.domain.contratos.contrato_fonte;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.fontes.FonteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "contrato_fonte")
@Entity(name = "ContratoFonte")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContratoFonteEntity {

    @EmbeddedId
    private ContratoFonteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idContrato")
    @JoinColumn(name = "id_contrato")
    private ContratoEntity contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFonte")
    @JoinColumn(name = "id_fonte")
    private FonteEntity fonte;

}
