package com.saneacre.gecons.domain.contrato_fornecedor_po;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContratoFornecedorPoRepository extends JpaRepository<ContratoFornecedorPoEntity, ContratoFornecedorPoId> {

    @Query("SELECT c FROM ContratoFornecedorPo c WHERE c.id = :id")
    Optional<ContratoFornecedorPoEntity> procuraChaveDuplicada(ContratoFornecedorPoId id);


}
