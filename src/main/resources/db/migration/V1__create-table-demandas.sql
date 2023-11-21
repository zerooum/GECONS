create table demandas (
    id serial primary key,
    nome varchar(255) not null unique,
    tipo varchar(20) not null,
    unidade varchar(20) not null,
    grupo varchar(20) not null,
    quantidade numeric(10,2),
    ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
)