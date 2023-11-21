package com.saneacre.gecons.domain.fornecedores;

import com.saneacre.gecons.domain.demanda.BuscaDemandasDTO;
import com.saneacre.gecons.domain.enums.PersonalidadeJuridica;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "fornecedores")
@Entity(name = "Fornecedor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FornecedorEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private PersonalidadeJuridica tipo;

    @Column(unique = true)
    private String nome;

    @Column(unique = true)
    private String documento;

    private String contato;

    private String email;

    private Boolean ativo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts_modificacao;

    public FornecedorEntity(CriaFornecedorDTO dados) {
        this.tipo = dados.tipo();
        this.nome = dados.nome();
        this.documento = dados.documento();
        this.contato = dados.contato();
        this.email = dados.email();
        this.ativo = true;
    }

    public void atualizar(BuscaFornecedoresDTO dados) {

        if (dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.documento() != null) {
            this.documento = dados.documento();
        }
        if (dados.contato() != null) {
            this.contato = dados.contato();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
