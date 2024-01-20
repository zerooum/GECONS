package com.saneacre.gecons.domain.contrato_fornecedor_po;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContratoFornecedorPoId {

    @Column(name = "id_contrato")
    private Long idContrato;

    @Column(name = "id_fornecedor")
    private Long idFornecedor;

    @Column(name = "id_demanda")
    private Long idDemanda;

}
