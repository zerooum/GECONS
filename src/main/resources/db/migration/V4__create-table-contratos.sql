create table contratos (
    id serial primary key,
    tipo varchar(8) not null,
    numero varchar(20) not null unique,
    objeto varchar(255) not null,
    data_assinatura date not null,
    valor numeric(12,2) not null
    data_vigencia date not null,
    numero_sei char(25),
    portaria varchar(12),
    fiscal_titular varchar(50),
    fiscal_substituto varchar(50),
    gestor_titular varchar(50),
    gestor_substituto varchar(50),
    ativo boolean not null,
    ts_criacao timestamp,
    ts_modificacao timestamp
)