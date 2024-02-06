package com.saneacre.gecons.domain.contratos.elemento_de_despesa;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ElementoDeDespesaService {

    @Autowired
    ElementoDeDespesaRepository elementoDeDespesaRepository;

    public ElementoDeDespesaEntity criaElementoDeDespesa(CriaElementoDeDespesaDTO dados) {
        var elemento = new ElementoDeDespesaEntity(dados);
        elementoDeDespesaRepository.save(elemento);
        return elemento;
    }

    public Page<RetornaElementoDeDespesaDTO> buscarTodosElementos(Pageable paginacao) {
        return elementoDeDespesaRepository.findAll(paginacao).map(RetornaElementoDeDespesaDTO::new);
    }

    public RetornaElementoDeDespesaDTO buscaElementoPorId(Long id) {
        var elemento = elementoDeDespesaRepository.findById(id);
        if (elemento.isEmpty())
            throw new EntityNotFoundException("Elemento de despesa com o id " + id + " não encontrado!");

        return new RetornaElementoDeDespesaDTO(elemento.get());
    }

    public ElementoDeDespesaEntity atualizaElemento(Long id, AtualizaElementoDeDespesaDTO dados) {
        var elemento = elementoDeDespesaRepository.findById(id);
        if (elemento.isEmpty())
            throw new EntityNotFoundException("Elemento de despesa com o id " + id + " não encontrado!");
        elemento.get().atualizar(dados);
        return elemento.get();
    }

    public void deletaElemento(Long id) {
        var elemento = elementoDeDespesaRepository.findById(id);
        if (elemento.isEmpty())
            throw new EntityNotFoundException("Elemento de despesa com o id " + id + " não encontrado!");

        elementoDeDespesaRepository.delete(elemento.get());
    }

}
