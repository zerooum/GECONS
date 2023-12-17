package com.saneacre.gecons.domain.demanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;

public interface DemandaRepository extends JpaRepository<DemandaEntity, Long> {
    Page<DemandaEntity> findAllByAtivoTrue(Pageable paginacao);                    
}
