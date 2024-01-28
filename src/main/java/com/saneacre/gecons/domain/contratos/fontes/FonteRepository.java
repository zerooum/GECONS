package com.saneacre.gecons.domain.contratos.fontes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FonteRepository extends JpaRepository<FonteEntity, Long> {
    Page<FonteEntity> findAllByAtivoTrue(Pageable paginacao);
}
