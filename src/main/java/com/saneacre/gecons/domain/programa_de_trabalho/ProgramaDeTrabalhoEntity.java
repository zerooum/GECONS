package com.saneacre.gecons.domain.programa_de_trabalho;

import com.saneacre.gecons.domain.contratos.CriaContratoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "programas_de_trabalho")
@Entity(name = "ProgramaDeTrabalho")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProgramaDeTrabalhoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String numero;

    private String descricao;

    private Boolean ativo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts_modificacao;

    public ProgramaDeTrabalhoEntity(CriaProgramaDeTrabalhoDTO dados) {
        this.numero = dados.numero();
        this.descricao = dados.descricao();
        this.ativo = true;
    }

    public void atualizar(AtualizaProgramaDeTrabalhoDTO dados) {
        if (dados.numero() != null)
            this.numero = dados.numero();

        if (dados.descricao() != null)
            this.descricao = dados.descricao();

    }

    public void excluir() {
        this.ativo = false;
    }
}
