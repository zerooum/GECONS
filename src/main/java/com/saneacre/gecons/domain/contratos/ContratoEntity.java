package com.saneacre.gecons.domain.contratos;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoEntity;
import com.saneacre.gecons.domain.contratos.contrato_elemento.ContratoElementoEntity;
import com.saneacre.gecons.domain.contratos.contrato_fonte.ContratoFonteEntity;
import com.saneacre.gecons.domain.contratos.contrato_programa.ContratoProgramaEntity;
import com.saneacre.gecons.domain.empenhos.EmpenhoEntity;
import com.saneacre.gecons.domain.enums.TiposContrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private TiposContrato tipo;

    @Column(unique = true)
    private String numero;

    private String objeto;
    private LocalDate dataAssinatura;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    private LocalDate dataVigencia;
    private String numeroSei;
    private String portaria;
    private String fiscalTitular;
    private String fiscalSubstituto;
    private String gestorTitular;
    private String gestorSubstituto;

    private Boolean ativo;

    @OneToMany(mappedBy = "contrato")
    Set<ContratoFornecedorPoEntity> contrato_fornecedor_demanda;

    @OneToMany(mappedBy = "contrato")
    Set<ContratoProgramaEntity> contrato_programa;

    @OneToMany(mappedBy = "contrato")
    Set<ContratoElementoEntity> contrato_elemento;

    @OneToMany(mappedBy = "contrato")
    Set<ContratoFonteEntity> contrato_fonte;

    @OneToMany(mappedBy = "contrato")
    Set<EmpenhoEntity> empenhos;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime ts_criacao;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ts_modificacao;

    public ContratoEntity(CriaContratoDTO dados) {
        this.tipo = dados.tipo();
        this.numero = dados.numero();
        this.objeto = dados.objeto();
        this.dataAssinatura = dados.dataAssinatura();
        this.valor = dados.valor();
        this.dataVigencia = dados.dataVigencia();
        this.numeroSei = dados.numeroSei();
        this.portaria = dados.portaria();
        this.fiscalTitular = dados.fiscalTitular();
        this.fiscalSubstituto = dados.fiscalTitular();
        this.gestorTitular = dados.gestorTitular();
        this.gestorSubstituto = dados.gestorSubstituto();
        this.ativo = true;
    }

    public void atualizar(AtualizaContratoDTO dados) {

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
