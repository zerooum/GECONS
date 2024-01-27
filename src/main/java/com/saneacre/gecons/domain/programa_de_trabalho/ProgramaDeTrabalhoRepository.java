package com.saneacre.gecons.domain.programa_de_trabalho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramaDeTrabalhoRepository extends JpaRepository<ProgramaDeTrabalhoEntity, Long> {
    Page <ProgramaDeTrabalhoEntity> findAllByAtivoTrue(Pageable paginacao);
}
