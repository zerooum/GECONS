package com.saneacre.gecons.domain.contratos.elemento_de_despesa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementoDeDespesaRepository extends JpaRepository<ElementoDeDespesaEntity, Long> {
    Page<ElementoDeDespesaEntity> findAllByAtivoTrue(Pageable paginacao);
    ElementoDeDespesaEntity findByNumero(String numero);

}
