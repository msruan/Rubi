create database rubi;

use rubi;

create table Perfil(
    id varchar(36) PRIMARY KEY,
    username varchar(30) UNIQUE NOT NULL,
    nome varchar(80) NOT NULL,
    email varchar(320) UNIQUE NOT NULL,
    biografia varchar(100) NULL
);

create table Postagem(
    id varchar(36) PRIMARY KEY,
    perfil_id varchar(36) NOT NULL,
    data DATETIME NOT NULL,
    texto varchar(400) NOT NULL,
    curtidas INT NOT NULL,
    descurtidas INT NOT NULL,
    visualizacoes_restantes INT NULL,
    hashtags varchar(150) NULL,
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id)
);