create table fontes (
    id serial primary key,
    numero varchar(30) not null unique,
    descricao varchar(255),
	ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
);

create table contrato_fonte (
    id_contrato bigint,
    id_fonte bigint,
    PRIMARY KEY (id_contrato, id_fonte),
    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_fonte) REFERENCES fontes(id)
)