package com.saneacre.gecons.domain.usuario;

import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemaPermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.UsuarioSistemasPermissoesId;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoDTO;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.permissoes.PermissaoRepository;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaEntity;
import com.saneacre.gecons.domain.usuario.sistemas_permissoes.sistemas.SistemaRepository;
import com.saneacre.gecons.infra.erros.UsuarioJaAdminException;
import com.saneacre.gecons.infra.erros.UsuarioJaExisteException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Page<RetornaUsuarioDTO> buscarTodosUsuarios(Pageable paginacao) {
        var pagina = usuarioRepository.findAll(paginacao);
        return usuarioRepository.findAll(paginacao).map(RetornaUsuarioDTO::new);
    }



    public UsuarioSistemaPermissaoEntity getUsuarioSistemaPermissao(PermissaoDTO dados) {
        var usuario = usuarioRepository.findByLogin(dados.usuario());
        if (usuario == null) throw new EntityNotFoundException("O usuario solicitado não existe!");

        var sistema = sistemaRepository.findByNome(dados.sistema());
        if (sistema == null) throw new EntityNotFoundException("O sistema solicitado não existe!");

        var permissao = permissaoRepository.findByNome(dados.permissao());
        if (permissao == null) throw new EntityNotFoundException("A permissão solicitada não existe!");

        UsuarioSistemasPermissoesId id = new UsuarioSistemasPermissoesId(usuario.getId(), sistema.getId(), permissao.getId());

        return new UsuarioSistemaPermissaoEntity(id, usuario, sistema, permissao);
    }

    public String[] getSistemasUsuarios(String nomeUsuario) {
        var usuario = usuarioRepository.findByLogin(nomeUsuario);
        if (usuario == null) throw new EntityNotFoundException("O usuario solicitado não existe!");
        if (Objects.equals(usuario.getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        List<UsuarioSistemaPermissaoEntity> usuarioSistemasPermissoes = usuarioSistemaPermissaoRepository.findAllByUsuario(usuario);

        return usuarioSistemasPermissoes.stream().map(UsuarioSistemaPermissaoEntity::getSistema)
                                       .distinct().map(SistemaEntity::getNome).toList().toArray(String[]::new);
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

    public UsuarioSistemaPermissaoEntity criaUsuarioSistemaPermissao(PermissaoDTO dados) {
        var usuarioSistemaPermissao = getUsuarioSistemaPermissao(dados);
        if (Objects.equals(usuarioSistemaPermissao.getUsuario().getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        var jaExiste = usuarioSistemaPermissaoRepository.procuraChaveDuplicada(usuarioSistemaPermissao.getId());
        if (jaExiste.isPresent()) throw new DataIntegrityViolationException("Usuário já possui a permissão!");

        usuarioSistemaPermissaoRepository.save(usuarioSistemaPermissao);
        return usuarioSistemaPermissao;
    }

    public void deletaUsuarioSistemaPermissao(PermissaoDTO dados) {
        var usuarioSistemaPermissao = getUsuarioSistemaPermissao(dados);
        if (Objects.equals(usuarioSistemaPermissao.getUsuario().getRole(), "ADMIN")) throw new UsuarioJaAdminException();

        var naoExiste = usuarioSistemaPermissaoRepository.procuraChaveDuplicada(usuarioSistemaPermissao.getId());
        if (naoExiste.isEmpty()) throw new EntityNotFoundException("Usuário não possui a permissão!");

        usuarioSistemaPermissaoRepository.delete(usuarioSistemaPermissao);
    }

    public void deletarUsuario(Long id) {
        System.out.println("chegou aqui");
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) throw new EntityNotFoundException("Usuario com o id " + id + " não encontrado!");
        usuarioRepository.delete(usuario.get());
    }
}
