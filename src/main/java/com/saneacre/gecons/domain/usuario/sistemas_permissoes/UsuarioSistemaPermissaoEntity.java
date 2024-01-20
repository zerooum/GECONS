package com.saneacre.gecons.domain.usuario.sistemas_permissoes;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuario_sistemas_permissoes")
@Entity(name = "UsuarioSistemaPermissao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioSistemaPermissaoEntity {

    @EmbeddedId
    private UsuarioSistemasPermissoesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSistema")
    @JoinColumn(name = "id_sistema")
    private SistemaEntity sistema;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermissao")
    @JoinColumn(name = "id_permissao")
    private PermissaoEntity permissao;


}
