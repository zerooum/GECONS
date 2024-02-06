package com.saneacre.gecons.domain.compras.itens;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.fornecedores.FornecedorEntity;
import com.saneacre.gecons.domain.plano_operativo.DemandaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompraItemRepository extends JpaRepository<CompraItemEntity, CompraItemId> {
    List<CompraItemEntity> findAllByContratoAndFornecedorAndDemanda(ContratoEntity contrato,
                                                                    FornecedorEntity fornecedor, DemandaEntity demanda);

    @Query("SELECT c FROM CompraItem c WHERE c.id = :id")
    Optional<CompraItemEntity> procuraChaveDuplicada(CompraItemId id);
}
