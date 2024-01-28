package com.saneacre.gecons.domain.contratos.fontes;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FonteService {

    @Autowired
    FonteRepository fonteRepository;

    public FonteEntity criaFonte(CriaFonteDTO dados) {
        var fonte = new FonteEntity(dados);
        fonteRepository.save(fonte);
        return fonte;
    }

    public Page<RetornaFonteDTO> buscarTodasFontes(Pageable paginacao) {
        return fonteRepository.findAllByAtivoTrue(paginacao).map(RetornaFonteDTO::new);
    }

    public RetornaFonteDTO buscaFontePorId(Long id) {
        var fonte = fonteRepository.findById(id);
        if (fonte.isEmpty() || !fonte.get().getAtivo())
            throw new EntityNotFoundException("Fonte com o id " + id + " não encontrado!");

        return new RetornaFonteDTO(fonte.get());
    }

    public FonteEntity atualizaFonte(Long id, AtualizaFonteDTO dados) {
        var fonte = fonteRepository.findById(id);
        if (fonte.isEmpty() || !fonte.get().getAtivo())
            throw new EntityNotFoundException("Fonte com o id " + id + " não encontrado!");
        fonte.get().atualizar(dados);
        return fonte.get();
    }

    public void deletaFonte(Long id) {
        var fonte = fonteRepository.findById(id);
        if (fonte.isEmpty() || !fonte.get().getAtivo())
            throw new EntityNotFoundException("Fonte com o id " + id + " não encontrado!");
        fonte.get().excluir();
    }
}
