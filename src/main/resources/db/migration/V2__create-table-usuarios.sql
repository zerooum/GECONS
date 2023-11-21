create table usuarios (
    id serial primary key,
    login varchar(50) not null unique,
    senha varchar(255) not null,
    ts_criacao timestamp
)