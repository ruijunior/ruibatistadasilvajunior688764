CREATE TABLE regionais (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_regionais_nome ON regionais(nome);
CREATE INDEX idx_regionais_ativo ON regionais(ativo);
