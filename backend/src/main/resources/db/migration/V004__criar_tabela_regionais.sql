CREATE TABLE regionais (
    id VARCHAR(255) PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    id_externo INTEGER,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_regionais_nome ON regionais(nome);
CREATE INDEX idx_regionais_ativo ON regionais(ativo);
CREATE INDEX idx_regionais_id_externo ON regionais(id_externo);
