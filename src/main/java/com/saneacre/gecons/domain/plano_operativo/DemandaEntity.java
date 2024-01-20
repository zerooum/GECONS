package com.saneacre.gecons.domain.plano_operativo;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.enums.Grupos;
import com.saneacre.gecons.domain.enums.TiposDemanda;
import com.saneacre.gecons.domain.enums.Unidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "demandas")
@Entity(name = "Demanda")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DemandaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Unidades unidade;

    @Enumerated(EnumType.STRING)
    private TiposDemanda tipo;

    @Enumerated(EnumType.STRING)
    private Grupos grupo;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantidade;

    private Boolean ativo;

    @OneToMany(mappedBy = "demanda")
    Set<ContratoFornecedorPoEntity> contrato_fornecedor_demanda;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ts_modificacao;

    public DemandaEntity(CriaDemandaDTO dados) {
        this.nome = dados.nome();
        this.unidade = dados.unidade();
        this.tipo = dados.tipo();
        this.grupo = dados.grupo();
        this.quantidade = dados.quantidade();
        this.ativo = true;
    }

    public void atualizar(AtualizaDemandaDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.unidade() != null) {
            this.unidade = dados.unidade();
        }
        if (dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
        if (dados.grupo() != null) {
            this.grupo = dados.grupo();
        }
        if (dados.quantidade() != null) {
            this.quantidade = dados.quantidade();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
