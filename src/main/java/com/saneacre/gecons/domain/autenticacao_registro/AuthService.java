package com.saneacre.gecons.domain.autenticacao_registro;

import com.saneacre.gecons.domain.usuario.UsuarioEntity;
import com.saneacre.gecons.domain.usuario.UsuarioRepository;
import com.saneacre.gecons.infra.erros.UsuarioJaExisteException;
import com.saneacre.gecons.infra.security.TokenService;
import com.saneacre.gecons.utils.RespostaSimplesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public void registraUsuario(RegistroDTO dados) {
        if(repository.findByLogin(dados.login()) != null) {
            throw new UsuarioJaExisteException();
        }
        boolean primeiroUsuario = repository.count() == 0;
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());

        UsuarioEntity novoUsuario;
        if (primeiroUsuario) {
            novoUsuario = new UsuarioEntity(dados.login(), senhaCriptografada, "ADMIN");
        } else {
            novoUsuario = new UsuarioEntity(dados.login(), senhaCriptografada, "USUARIO");
        }
        this.repository.save(novoUsuario);
    }

}
