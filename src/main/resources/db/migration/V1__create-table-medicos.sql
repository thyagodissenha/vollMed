CREATE TABLE medicos(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    crm VARCHAR(6) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100) NULL,
    numero VARCHAR(20) NULL,
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT(true)
);

CREATE TABLE pacientes(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100) NULL,
    numero VARCHAR(20) NULL,
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT(true)
);

CREATE TABLE usuarios(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE consultas(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    motivo_cancelamento VARCHAR(100),
    CONSTRAINT fk_medido
        FOREIGN KEY(medico_id)
            REFERENCES medicos(id),
    CONSTRAINT fk_paciente
        FOREIGN KEY(paciente_id)
            REFERENCES pacientes(id)
);

INSERT INTO usuarios (login, senha) VALUES ('admin@voll.med','$2a$12$KTmayop4oeZETPl8srhTHOgSiPZk8KlfkB57LPa0d9ZPm42V5Akqq');
