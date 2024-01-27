create table programas_de_trabalho (
    id serial primary key,
    numero varchar(30) not null unique,
    descricao varchar(255),
	ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
);

create table contrato_programa (
    id_contrato bigint,
    id_programa bigint,
    PRIMARY KEY (id_contrato, id_programa),
    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_programa) REFERENCES programas_de_trabalho(id)
)