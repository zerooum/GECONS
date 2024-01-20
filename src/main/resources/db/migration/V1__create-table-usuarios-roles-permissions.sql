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
    id_usuario int,
    id_sistema int,
    id_permissao int,
    PRIMARY KEY (id_usuario, id_sistema, id_permissao),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_sistema) REFERENCES sistemas(id),
    FOREIGN KEY (id_permissao) REFERENCES permissoes(id)
);


