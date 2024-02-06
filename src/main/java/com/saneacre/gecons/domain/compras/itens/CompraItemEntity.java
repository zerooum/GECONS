package com.saneacre.gecons.domain.compras.itens;

import com.saneacre.gecons.domain.compras.CompraEntity;
import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import com.saneacre.gecons.domain.fornecedores.FornecedorEntity;
import com.saneacre.gecons.domain.plano_operativo.DemandaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "compra_item")
@Entity(name = "CompraItem")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CompraItemEntity {

    @EmbeddedId
    private CompraItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCompra")
    @JoinColumn(name = "id_compra")
    private CompraEntity compra;

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

    private BigDecimal quantidade;

}
