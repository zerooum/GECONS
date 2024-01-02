package com.saneacre.gecons.domain.usuario.sistemas_permissoes;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
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

    @ManyToOne
    @MapsId("id_usuario")
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @MapsId("id_sistema")
    @JoinColumn(name = "id_sistema")
    private SistemaEntity sistema;

    @ManyToOne
    @MapsId("id_permissao")
    @JoinColumn(name = "id_permissao")
    private PermissaoEntity permissao;

}
