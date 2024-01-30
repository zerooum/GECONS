package com.saneacre.gecons.domain.contratos.contrato_fonte;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.fontes.FonteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContratoFonteRepository extends JpaRepository<ContratoFonteEntity, ContratoFonteId> {

    @Query("SELECT c FROM ContratoFonte c WHERE c.id = :id")
    Optional<ContratoFonteEntity> procuraChaveDuplicada(ContratoFonteId id);

    List<ContratoFonteEntity> findByContrato(ContratoEntity contrato);
    List<ContratoFonteEntity> findByFonte(FonteEntity fonte);
}
