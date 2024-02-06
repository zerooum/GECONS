package com.saneacre.gecons.domain.plano_operativo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<DemandaEntity, Long> {

    DemandaEntity findByNome(String fornecedor);
}
