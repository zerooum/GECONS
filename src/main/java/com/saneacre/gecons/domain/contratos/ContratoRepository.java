package com.saneacre.gecons.domain.contratos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<ContratoEntity, Long> {
    Page<ContratoEntity> findAllByAtivoTrue(Pageable paginacao);
    ContratoEntity findByNumero(String numero);
}
