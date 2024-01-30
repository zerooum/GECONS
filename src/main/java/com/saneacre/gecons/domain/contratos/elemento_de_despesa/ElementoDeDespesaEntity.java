package com.saneacre.gecons.domain.contratos.elemento_de_despesa;

import com.saneacre.gecons.domain.contratos.contrato_elemento.ContratoElementoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Table(name = "elementos_de_despesa")
@Entity(name = "ElementoDeDespesa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ElementoDeDespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String numero;

    private String descricao;

    private Boolean ativo;

    @OneToMany(mappedBy = "elemento")
    Set<ContratoElementoEntity> elemento_contrato;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts_modificacao;

    public ElementoDeDespesaEntity(CriaElementoDeDespesaDTO dados) {
        this.numero = dados.numero();
        this.descricao = dados.descricao();
        this.ativo = true;
    }

    public void atualizar(AtualizaElementoDeDespesaDTO dados) {
        if (dados.numero() != null)
            this.numero = dados.numero();

        if (dados.descricao() != null)
            this.descricao = dados.descricao();

    }

    public void excluir() {
        this.ativo = false;
    }

}
