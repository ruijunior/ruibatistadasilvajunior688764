CREATE TABLE artistas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('CANTOR', 'BANDA')),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_artistas_nome ON artistas(nome);
CREATE INDEX idx_artistas_tipo ON artistas(tipo);
