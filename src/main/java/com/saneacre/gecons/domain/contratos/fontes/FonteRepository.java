package com.saneacre.gecons.domain.contratos.fontes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FonteRepository extends JpaRepository<FonteEntity, Long> {
    FonteEntity findByNumero(String numero);
}
