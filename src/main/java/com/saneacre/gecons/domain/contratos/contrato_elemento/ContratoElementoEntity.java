package com.saneacre.gecons.domain.contratos.contrato_elemento;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "contrato_elemento")
@Entity(name = "ContratoElemento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContratoElementoEntity {

    @EmbeddedId
    private ContratoElementoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idContrato")
    @JoinColumn(name = "id_contrato")
    private ContratoEntity contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idElemento")
    @JoinColumn(name = "id_elemento")
    private ElementoDeDespesaEntity elemento;
}
