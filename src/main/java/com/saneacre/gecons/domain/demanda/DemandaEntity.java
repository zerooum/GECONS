package com.saneacre.gecons.domain.demanda;

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
import java.util.Date;

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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts_modificacao;

    public DemandaEntity(CriaDemandaDTO dados) {
        this.nome = dados.nome();
        this.unidade = dados.unidade();
        this.tipo = dados.tipo();
        this.grupo = dados.grupo();
        this.quantidade = dados.quantidade();
        this.ativo = true;
    }

    public void atualizar(BuscaDemandasDTO dados) {
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
