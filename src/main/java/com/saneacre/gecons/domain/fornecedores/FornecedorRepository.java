package com.saneacre.gecons.domain.fornecedores;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    FornecedorEntity findByNome(String fornecedor);
}
