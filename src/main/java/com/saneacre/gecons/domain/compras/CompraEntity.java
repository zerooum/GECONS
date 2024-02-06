package com.saneacre.gecons.domain.compras;

import com.saneacre.gecons.domain.compras.itens.CompraItemEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "compras")
@Entity(name = "Compra")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_empenho", nullable = false)
    private EmpenhoEntity empenho;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.REMOVE)
    private Set<CompraItemEntity> itens;

    public CompraEntity(EmpenhoEntity empenho, BigDecimal valor, LocalDate data) {
        this.empenho = empenho;
        this.valor = valor;
        this.data = data;
    }
}
