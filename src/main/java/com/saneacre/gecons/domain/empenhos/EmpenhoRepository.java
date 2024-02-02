package com.saneacre.gecons.domain.empenhos;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpenhoRepository extends JpaRepository<EmpenhoEntity, Long> {

    List<EmpenhoEntity> findByContrato(ContratoEntity contrato);
}
