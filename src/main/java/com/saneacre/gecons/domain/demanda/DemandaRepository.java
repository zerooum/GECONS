package com.saneacre.gecons.domain.demanda;

import com.saneacre.gecons.domain.demanda.DemandaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<DemandaEntity, Long> {
    Page<DemandaEntity> findAllByAtivoTrue(Pageable paginacao);
}
