package com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {
    PermissaoEntity findByNome(String nome);
}
