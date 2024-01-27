package com.saneacre.gecons.domain.programa_de_trabalho;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProgramaDeTrabalhoService {

    @Autowired
    ProgramaDeTrabalhoRepository programaDeTrabalhoRepository;

    public ProgramaDeTrabalhoEntity criaProgramaDeTrabalho(CriaProgramaDeTrabalhoDTO dados) {
        var programa = new ProgramaDeTrabalhoEntity(dados);
        programaDeTrabalhoRepository.save(programa);
        return programa;
    }

    public Page<RetornaProgramaDeTrabalhoDTO> buscarTodosProgramas(Pageable paginacao) {
        return programaDeTrabalhoRepository.findAllByAtivoTrue(paginacao).map(RetornaProgramaDeTrabalhoDTO::new);
    }

    public RetornaProgramaDeTrabalhoDTO buscaProgramaPorId(Long id) {
        var programa = programaDeTrabalhoRepository.findById(id);
        if (programa.isEmpty() || !programa.get().getAtivo())
            throw new EntityNotFoundException("programa com o id " + id + " não encontrado!");

        return new RetornaProgramaDeTrabalhoDTO(programa.get());
    }

    public ProgramaDeTrabalhoEntity atualizaPrograma(Long id, AtualizaProgramaDeTrabalhoDTO dados) {
        var programa = programaDeTrabalhoRepository.findById(id);
        if (programa.isEmpty() || !programa.get().getAtivo())
            throw new EntityNotFoundException("Programa com o id " + id + " não encontrado!");
        programa.get().atualizar(dados);
        return programa.get();
    }

    public void deletaPrograma(Long id) {
        var programa = programaDeTrabalhoRepository.findById(id);
        if (programa.isEmpty() || !programa.get().getAtivo())
            throw new EntityNotFoundException("Programa com o id " + id + " não encontrado!");
        programa.get().excluir();
    }
}
