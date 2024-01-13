package com.saneacre.gecons.domain.fornecedores;

import com.saneacre.gecons.domain.enums.PersonalidadeJuridica;

public record RetornaFornecedorDTO(
                                   Long id,
                                   PersonalidadeJuridica tipo,
                                   String nome,
                                   String documento,
                                   String contato,
                                   String email) {

    public RetornaFornecedorDTO(FornecedorEntity fornecedor){
        this(fornecedor.getId(), fornecedor.getTipo(), fornecedor.getNome(), fornecedor.getDocumento(),
                fornecedor.getContato(), fornecedor.getEmail());
    }
}
