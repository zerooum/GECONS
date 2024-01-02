package com.saneacre.gecons.domain.usuario;

import com.saneacre.gecons.domain.usuario.sistemas_permissoes.SistemaEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

        @OneToMany(mappedBy = "usuario")
        Set<UsuarioSistemaPermissaoEntity> usuarios;

        public UsuarioEntity(String login, String senha, String role) {
            this.login = login;
            this.senha = senha;
            this.role = role;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if(this.role == "ADMIN") return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                                                               new SimpleGrantedAuthority("ROLE_USER"));

            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

