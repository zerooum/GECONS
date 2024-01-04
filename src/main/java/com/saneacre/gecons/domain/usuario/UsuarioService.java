package com.saneacre.gecons.domain.usuario;

import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemasPermissoesId;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SistemaRepository sistemaRepository;

    @Autowired
    PermissaoRepository permissaoRepository;

    @Autowired
    UsuarioSistemaPermissaoRepository repo;

    public UsuarioSistemaPermissaoEntity getUsuarioSistemaPermissao(String nomeUsuario, String nomeSistema, String nomePermissao) {
        var usuario = usuarioRepository.findByLogin(nomeUsuario);
        var sistema = sistemaRepository.findByNome(nomeSistema);
        var permissao = permissaoRepository.findByNome(nomePermissao);

        if (usuario == null || sistema == null || permissao == null) {
            throw new EntityNotFoundException();
        }

        UsuarioSistemasPermissoesId id = new UsuarioSistemasPermissoesId(usuario.getId(), sistema.getId(), permissao.getId());
        UsuarioSistemaPermissaoEntity usuarioSistemaPermissao = new UsuarioSistemaPermissaoEntity(id, usuario, sistema, permissao);
        return usuarioSistemaPermissao;

    }


}
