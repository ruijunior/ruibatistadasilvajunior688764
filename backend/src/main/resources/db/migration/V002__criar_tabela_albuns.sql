CREATE TABLE albuns (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    ano_lancamento INTEGER,
    url_capa VARCHAR(500),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_albuns_titulo ON albuns(titulo);
CREATE INDEX idx_albuns_ano ON albuns(ano_lancamento);
