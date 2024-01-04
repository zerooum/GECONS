package com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "sistemas")
@Entity(name = "Sistema")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SistemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @OneToMany(mappedBy = "sistema")
    Set<UsuarioSistemaPermissaoEntity> sistemas;

}
