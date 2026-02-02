-- Alterar tabelas para usar UUID String ao invés de BIGSERIAL

-- 1. Artistas
ALTER TABLE artistas DROP CONSTRAINT IF EXISTS artistas_pkey;
ALTER TABLE artistas ALTER COLUMN id TYPE VARCHAR(255);
ALTER TABLE artistas ADD PRIMARY KEY (id);

-- 2. Albuns
ALTER TABLE albuns DROP CONSTRAINT IF EXISTS albuns_pkey;
ALTER TABLE albuns ALTER COLUMN id TYPE VARCHAR(255);
ALTER TABLE albuns ADD PRIMARY KEY (id);

-- Adicionar url_capa se não existir
ALTER TABLE albuns ADD COLUMN IF NOT EXISTS url_capa VARCHAR(500);

-- 3. Regionais
ALTER TABLE regionais DROP CONSTRAINT IF EXISTS regionais_pkey;
ALTER TABLE regionais ALTER COLUMN id TYPE VARCHAR(255);
ALTER TABLE regionais ADD PRIMARY KEY (id);

-- Adicionar id_externo para sincronização com API
ALTER TABLE regionais ADD COLUMN IF NOT EXISTS id_externo INTEGER;
CREATE INDEX IF NOT EXISTS idx_regionais_id_externo ON regionais(id_externo);
