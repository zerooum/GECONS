package com.saneacre.gecons.domain.usuario.sistemas_permissoes;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioSistemaPermissaoRepository extends JpaRepository<UsuarioSistemaPermissaoEntity, Long> {

    List<UsuarioSistemaPermissaoEntity> findAllByUsuario(UsuarioEntity usuario);
}
