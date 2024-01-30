package com.saneacre.gecons.domain.contratos.contrato_elemento;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContratoElementoRepository extends JpaRepository<ContratoElementoEntity, ContratoElementoId> {

    @Query("SELECT c FROM ContratoElemento c WHERE c.id = :id")
    Optional<ContratoElementoEntity> procuraChaveDuplicada(ContratoElementoId id);

    List<ContratoElementoEntity> findByContrato(ContratoEntity contrato);
    List<ContratoElementoEntity> findByElemento(ElementoDeDespesaEntity programa);
}
