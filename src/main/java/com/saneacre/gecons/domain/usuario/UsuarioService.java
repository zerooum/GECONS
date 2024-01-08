package com.saneacre.gecons.domain.usuario;

import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemasPermissoesId;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaRepository;
import com.saneacre.gecons.infra.erros.UsuarioJaAdminException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SistemaRepository sistemaRepository;

    @Autowired
    PermissaoRepository permissaoRepository;

    @Autowired
    UsuarioSistemaPermissaoRepository usuarioSistemaPermissaoRepository;

    public UsuarioSistemaPermissaoEntity getUsuarioSistemaPermissao(String nomeUsuario, String nomeSistema, String nomePermissao) {
        var usuario = usuarioRepository.findByLogin(nomeUsuario);
        if (usuario == null) throw new EntityNotFoundException("O usuario solicitado não existe!");

        var sistema = sistemaRepository.findByNome(nomeSistema);
        if (sistema == null) throw new EntityNotFoundException("O sistema solicitado não existe!");

        var permissao = permissaoRepository.findByNome(nomePermissao);
        if (permissao == null) throw new EntityNotFoundException("A permissão solicitada não existe!");

        UsuarioSistemasPermissoesId id = new UsuarioSistemasPermissoesId(usuario.getId(), sistema.getId(), permissao.getId());
        return new UsuarioSistemaPermissaoEntity(id, usuario, sistema, permissao);
    }

    public String[] getSistemasUsuarios(String nomeUsuario) {
        var usuario = usuarioRepository.findByLogin(nomeUsuario);
        if (usuario == null) throw new EntityNotFoundException("O usuario solicitado não existe!");
        if (Objects.equals(usuario.getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        List<UsuarioSistemaPermissaoEntity> usuarioSistemasPermissoes = usuarioSistemaPermissaoRepository.findAllByUsuario(usuario);

        String[] sistemas = usuarioSistemasPermissoes.stream().map(UsuarioSistemaPermissaoEntity::getSistema)
                                       .distinct().map(SistemaEntity::getNome).toList().toArray(String[]::new);

        return sistemas;
    }

    public String[] getPermissoesUsuarios(String nomeUsuario) {
        var usuario = usuarioRepository.findByLogin(nomeUsuario);
        if (usuario == null) throw new EntityNotFoundException("O usuario solicitado não existe!");
        if (Objects.equals(usuario.getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        List<UsuarioSistemaPermissaoEntity> usuarioSistemasPermissoes = usuarioSistemaPermissaoRepository.findAllByUsuario(usuario);

        ArrayList<String> permissoes = new ArrayList<>();
        usuarioSistemasPermissoes.forEach(obj -> {
            String permissao = obj.getSistema().getNome() + "_" + obj.getPermissao().getNome();
            permissoes.add(permissao);
        });

        return permissoes.toArray(String[]::new);

    }
}
