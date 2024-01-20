package com.saneacre.gecons.domain.usuario.sistemas_permissoes;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioSistemaPermissaoRepository extends JpaRepository<UsuarioSistemaPermissaoEntity, UsuarioSistemasPermissoesId> {

    List<UsuarioSistemaPermissaoEntity> findAllByUsuario(UsuarioEntity usuario);

    @Query("SELECT u FROM UsuarioSistemaPermissao u WHERE u.id = :id")
    Optional<UsuarioSistemaPermissaoEntity> procuraChaveDuplicada(UsuarioSistemasPermissoesId id);
}
