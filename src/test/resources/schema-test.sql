CREATE TABLE IF NOT EXISTS integrante (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    franquia VARCHAR(255) NOT NULL,
    funcao VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS time (
    id BIGINT PRIMARY KEY,
    data DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS composicao_time (
    id BIGINT PRIMARY KEY,
    id_time BIGINT NOT NULL,
    id_integrante BIGINT NOT NULL,
    FOREIGN KEY (id_time) REFERENCES time(id),
    FOREIGN KEY (id_integrante) REFERENCES integrante(id)
);