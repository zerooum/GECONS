create table usuarios (
    id serial primary key,
    login varchar(50) not null unique,
    senha varchar(255) not null,
    role varchar(20) not null,
    ts_criacao timestamp
);

create table sistemas (
    id serial primary key,
    nome varchar(20) not null unique
);

insert into sistemas (nome) values ('PLANO_OPERATIVO'), ('FORNECEDORES'), ('CONTRATOS');

create table permissoes (
    id serial primary key,
    nome varchar(20) not null unique
);

insert into permissoes (nome) values ('VISUALIZAR'),('INSERIR'), ('ATUALIZAR'), ('DELETAR');

create table usuario_sistemas_permissoes (
    id_usuario int references usuarios(id),
    id_sistema int references sistemas(id),
    id_permissao int references permissoes(id),
    PRIMARY KEY (id_usuario, id_sistema, id_permissao)
);


