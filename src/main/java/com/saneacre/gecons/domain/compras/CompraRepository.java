package com.saneacre.gecons.domain.compras;

import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    List<CompraEntity> findAllByEmpenho(EmpenhoEntity empenho);
}
