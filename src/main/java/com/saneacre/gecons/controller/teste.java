package com.saneacre.gecons.controller;

import com.saneacre.gecons.domain.contrato_fornecedor_po.ContratoFornecedorPoRepository;
import com.saneacre.gecons.domain.contratos.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/teste")
public class teste {

    @Autowired
    ContratoFornecedorPoRepository contratoFornecedorPoRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @GetMapping
    public void teste() {
        var contrato = contratoRepository.findById(7L).get();
        var contratos = contratoFornecedorPoRepository.findByContrato(contrato);

        var resultado = contratos.stream()
                .map(contrato1 -> contrato1.getQuantRegistro().multiply(contrato1.getValorUnitario()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(resultado);
    }
}
