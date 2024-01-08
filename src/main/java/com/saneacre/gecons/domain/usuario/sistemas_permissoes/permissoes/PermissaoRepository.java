package com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {
    PermissaoEntity findByNome(String nome);
}
