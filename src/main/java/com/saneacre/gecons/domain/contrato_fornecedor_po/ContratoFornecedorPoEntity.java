package com.saneacre.gecons.domain.contrato_fornecedor_po;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.fornecedores.FornecedorEntity;
import com.saneacre.gecons.domain.plano_operativo.DemandaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "contrato_fornecedor_demanda")
@Entity(name = "ContratoFornecedorPo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContratoFornecedorPoEntity {

    @EmbeddedId
    private ContratoFornecedorPoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idContrato")
    @JoinColumn(name = "id_contrato")
    private ContratoEntity contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFornecedor")
    @JoinColumn(name = "id_fornecedor")
    private FornecedorEntity fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDemanda")
    @JoinColumn(name = "id_demanda")
    private DemandaEntity demanda;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantConsumo;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantRegistro;

    @Column(precision = 12, scale = 2)
    private BigDecimal valorUnitario;

}
