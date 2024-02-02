package com.saneacre.gecons.domain.empenhos;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaEntity;
import com.saneacre.gecons.domain.contratos.fontes.FonteEntity;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "empenhos")
@Entity(name = "Empenho")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EmpenhoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "id_contrato", nullable = false)
    private ContratoEntity contrato;

    @ManyToOne()
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaDeTrabalhoEntity programa;

    @ManyToOne()
    @JoinColumn(name = "id_elemento", nullable = false)
    private ElementoDeDespesaEntity elemento;

    @ManyToOne()
    @JoinColumn(name = "id_fonte", nullable = false)
    private FonteEntity fonte;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate data;

    private String descricao;

}
