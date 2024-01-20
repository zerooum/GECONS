create table contrato_fornecedor_demanda (
    id_contrato bigint,
    id_fornecedor bigint,
    id_demanda bigint,
	quant_consumo numeric(10,2),
	quant_registro numeric(10,2) not null,
	valor_unitario numeric(12,2) not null,
    PRIMARY KEY (id_contrato, id_fornecedor, id_demanda),
    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id),
    FOREIGN KEY (id_demanda) REFERENCES demandas(id)
)