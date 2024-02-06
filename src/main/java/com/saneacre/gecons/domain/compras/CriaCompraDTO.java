package com.saneacre.gecons.domain.compras;

import com.saneacre.gecons.domain.compras.itens.Item;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CriaCompraDTO(@Positive(message = "ID de empenho inválido!")
                            Long idEmpenho,
                            @NotNull @Size(min = 1, message = "Deve ser informado ao menos um item para cadastrar a compra!")
                            List<Item> itens,
                            @NotNull(message = "A data da compra deve ser informada!")
                            LocalDate data ) {
}
