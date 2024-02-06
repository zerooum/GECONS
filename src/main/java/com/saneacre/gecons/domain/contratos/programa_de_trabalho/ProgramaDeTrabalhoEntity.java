package com.saneacre.gecons.domain.contratos.programa_de_trabalho;

import com.saneacre.gecons.domain.contratos.contrato_programa.ContratoProgramaEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

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

    @OneToMany(mappedBy = "programa")
    Set<ContratoProgramaEntity> programa_contrato;

    @OneToMany(mappedBy = "programa")
    Set<EmpenhoEntity> empenhos;

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
    }

    public void atualizar(AtualizaProgramaDeTrabalhoDTO dados) {
        if (dados.numero() != null)
            this.numero = dados.numero();

        if (dados.descricao() != null)
            this.descricao = dados.descricao();

    }

}
