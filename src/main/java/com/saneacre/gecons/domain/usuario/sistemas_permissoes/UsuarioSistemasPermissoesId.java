package com.saneacre.gecons.domain.usuario.sistemas_permissoes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class UsuarioSistemasPermissoesId implements Serializable {

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_sistema")
    private Long idSistema;

    @Column(name = "id_permissao")
    private Long idPermissao;

}
