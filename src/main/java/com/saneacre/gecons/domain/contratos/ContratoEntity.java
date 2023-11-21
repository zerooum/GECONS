package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.enums.TiposContrato;
import com.saneacre.gecons.domain.fornecedores.BuscaFornecedoresDTO;
import com.saneacre.gecons.domain.fornecedores.CriaFornecedorDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "contratos")
@Entity(name = "Contrato")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContratoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    private TiposContrato tipo;

    @Column(unique = true)
    private String numero;

    private String objeto;
    private Date dataAssinatura;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    private Date dataVigencia;
    private String numeroSei;
    private String portaria;
    private String fiscalTitular;
    private String fiscalSubstituto;
    private String gestorTitular;
    private String gestorSubstituto;

    private Boolean ativo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts_modificacao;

    public ContratoEntity(CriaContratoDTO dados) {
        this.tipo = dados.tipo();
        this.numero = dados.numero();
        this.objeto = dados.objeto();
        this.dataAssinatura = dados.dataAssinatura();
        this.dataVigencia = dados.dataVigencia();
        this.numeroSei = dados.numeroSei();
        this.portaria = dados.portaria();
        this.fiscalTitular = dados.fiscalTitular();
        this.fiscalSubstituto = dados.fiscalTitular();
        this.gestorTitular = dados.gestorTitular();
        this.gestorSubstituto = dados.gestorSubstituto();
        this.ativo = true;
    }

    public void atualizar(BuscaContratoDTO dados) {

        if (dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.objeto() != null) {
            this.objeto = dados.objeto();
        }
        if (dados.dataAssinatura() != null) {
            this.dataAssinatura = dados.dataAssinatura();
        }
        if (dados.dataVigencia() != null) {
            this.dataVigencia = dados.dataVigencia();
        }
        if (dados.numeroSei() != null) {
            this.numeroSei = dados.numeroSei();
        }
        if (dados.portaria() != null) {
            this.portaria = dados.portaria();
        }
        if (dados.fiscalTitular() != null) {
            this.fiscalTitular = dados.fiscalTitular();
        }
        if (dados.fiscalSubstituto() != null) {
            this.fiscalSubstituto = dados.fiscalSubstituto();
        }
        if (dados.gestorTitular() != null) {
            this.gestorTitular = dados.gestorTitular();
        }
        if (dados.gestorSubstituto() != null) {
            this.gestorSubstituto = dados.gestorSubstituto();
        }

    }

    public void excluir() {
        this.ativo = false;
    }

}
