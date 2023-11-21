package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<ContratoEntity, Long> {
    Page<ContratoEntity> findAllByAtivoTrue(Pageable paginacao);
}
