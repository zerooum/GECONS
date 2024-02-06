create table compras (
    id serial primary key,
    id_empenho bigint not null,
    valor numeric(12,2) not null,
    data date not null,

    FOREIGN KEY (id_empenho) REFERENCES empenhos(id)
);

insert into sistemas (nome) values ('COMPRAS');