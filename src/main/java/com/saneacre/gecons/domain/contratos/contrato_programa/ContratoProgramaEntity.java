package com.saneacre.gecons.domain.contratos.contrato_programa;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "contrato_programa")
@Entity(name = "ContratoPrograma")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContratoProgramaEntity {

    @EmbeddedId
    private ContratoProgramaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idContrato")
    @JoinColumn(name = "id_contrato")
    private ContratoEntity contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPrograma")
    @JoinColumn(name = "id_programa")
    private ProgramaDeTrabalhoEntity programa;

}
