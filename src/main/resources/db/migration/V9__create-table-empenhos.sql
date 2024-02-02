create table empenhos (
    id serial primary key,
    id_contrato bigint not null,
    id_programa bigint not null,
    id_elemento bigint not null,
    id_fonte bigint not null,
    numero varchar(25) not null unique,
    valor numeric(12,2) not null,
    data date not null,
    descricao varchar(255),

    FOREIGN KEY (id_contrato) REFERENCES contratos(id),
    FOREIGN KEY (id_programa) REFERENCES programas_de_trabalho(id),
    FOREIGN KEY (id_elemento) REFERENCES elementos_de_despesa(id),
    FOREIGN KEY (id_fonte) REFERENCES fontes(id)
);