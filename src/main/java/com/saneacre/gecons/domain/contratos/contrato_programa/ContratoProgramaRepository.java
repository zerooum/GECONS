package com.saneacre.gecons.domain.contratos.contrato_programa;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContratoProgramaRepository extends JpaRepository<ContratoProgramaEntity, ContratoProgramaId> {

    @Query("SELECT c FROM ContratoPrograma c WHERE c.id = :id")
    Optional<ContratoProgramaEntity> procuraChaveDuplicada(ContratoProgramaId id);

    List<ContratoProgramaEntity> findByContrato(ContratoEntity contrato);
    List<ContratoProgramaEntity> findByPrograma(ProgramaDeTrabalhoEntity programa);
}
