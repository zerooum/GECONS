create table compra_item (
    id_compra bigint,
    id_contrato bigint,
    id_fornecedor bigint,
    id_demanda bigint,
	quantidade numeric(10,2) not null,
    PRIMARY KEY (id_compra, id_contrato, id_fornecedor, id_demanda),
    FOREIGN KEY (id_compra) REFERENCES compras(id),
    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id),
    FOREIGN KEY (id_demanda) REFERENCES demandas(id)
)