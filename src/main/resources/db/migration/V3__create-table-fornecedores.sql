create table fornecedores (
    id serial primary key,
    tipo varchar(2) not null,
    nome varchar(250) not null unique,
    documento varchar(20) not null unique,
    contato varchar(16),
    email varchar(50),
    ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
)