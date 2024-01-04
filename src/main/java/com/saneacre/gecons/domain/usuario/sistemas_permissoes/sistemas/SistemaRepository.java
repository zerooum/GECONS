package com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaRepository extends JpaRepository<SistemaEntity, Long> {
    SistemaEntity findByNome(String nome);
}
