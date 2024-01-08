package com.saneacre.gecons.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String login;

        private String senha;

        private String role;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(updatable = false)
        private Date ts_criacao;

        @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
        Set<UsuarioSistemaPermissaoEntity> usuario_permissao_sistema;

        public UsuarioEntity(String login, String senha, String role) {
            this.login = login;
            this.senha = senha;
            this.role = role;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if(Objects.equals(this.role, "ADMIN"))  {
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            if (usuario_permissao_sistema.isEmpty()) return List.of(new SimpleGrantedAuthority("ROLE_USER"));

            // pega sistemas que o usuario tem acesso
            var permissoes = new ArrayList<>(
                                      usuario_permissao_sistema.stream().map(UsuarioSistemaPermissaoEntity::getSistema)
                                     .map(SistemaEntity::getNome).distinct().map(nomeSistema -> "ROLE_" + nomeSistema)
                                     .map(SimpleGrantedAuthority::new).toList()
                                      );

            // pega permissões que o usuario tem em cada sistema
            usuario_permissao_sistema.forEach(s -> {
                var permissao = "ROLE_" + s.getSistema().getNome() + "_" + s.getPermissao().getNome();
                permissoes.add(new SimpleGrantedAuthority(permissao));
            });

            return permissoes;
        }

        @Override
        public String getPassword() {
            return senha;
        }

        @Override
        public String getUsername() {
            return login;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

