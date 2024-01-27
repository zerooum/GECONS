create table elementos_de_despesa (
    id serial primary key,
    numero varchar(30) not null unique,
    descricao varchar(255),
	ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
);

create table contrato_elemento (
    id_contrato bigint,
    id_elemento bigint,
    PRIMARY KEY (id_contrato, id_elemento),
    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_elemento) REFERENCES elementos_de_despesa(id)
)