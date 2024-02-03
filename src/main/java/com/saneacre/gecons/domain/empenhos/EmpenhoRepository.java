package com.saneacre.gecons.domain.empenhos;

import com.saneacre.gecons.domain.contratos.ContratoEntity;
import com.saneacre.gecons.domain.contratos.elemento_de_despesa.ElementoDeDespesaEntity;
import com.saneacre.gecons.domain.contratos.fontes.FonteEntity;
import com.saneacre.gecons.domain.contratos.programa_de_trabalho.ProgramaDeTrabalhoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpenhoRepository extends JpaRepository<EmpenhoEntity, Long> {

    List<EmpenhoEntity> findByContrato(ContratoEntity contrato);
    List<EmpenhoEntity> findByContratoAndPrograma(ContratoEntity contrato, ProgramaDeTrabalhoEntity programa);
    List<EmpenhoEntity> findByContratoAndElemento(ContratoEntity contrato, ElementoDeDespesaEntity elemento);
    List<EmpenhoEntity> findByContratoAndFonte(ContratoEntity contrato, FonteEntity fonte);
}
