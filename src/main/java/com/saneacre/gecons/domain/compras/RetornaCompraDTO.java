package com.saneacre.gecons.domain.compras;

import com.saneacre.gecons.domain.compras.itens.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record RetornaCompraDTO(Long idCompra, Long idEmpenho, BigDecimal valor, LocalDate data, List<Item> itens) {

    public RetornaCompraDTO(CompraEntity dados, List<Item> itens) {
        this(dados.getId(), dados.getEmpenho().getId(), dados.getValor(), dados.getData(), itens);
    }
}
