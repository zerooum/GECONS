package com.saneacre.gecons.domain.programa_de_trabalho;

import org.springframework.beans.factory.annotation.Autowired;
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
}
