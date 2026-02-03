-- Alterar tabelas para usar UUID String ao invés de BIGSERIAL

-- 0. Remover constraints da tabela de relacionamento (dependem das PKs)
-- Necessário dropar antes de alterar o tipo das colunas referenciadas
ALTER TABLE artista_album DROP CONSTRAINT IF EXISTS artista_album_artista_id_fkey;
ALTER TABLE artista_album DROP CONSTRAINT IF EXISTS artista_album_album_id_fkey;

-- 1. Artistas
ALTER TABLE artistas DROP CONSTRAINT IF EXISTS artistas_pkey;
ALTER TABLE artistas ALTER COLUMN id TYPE VARCHAR(255);
ALTER TABLE artistas ADD PRIMARY KEY (id);

-- 2. Albuns
ALTER TABLE albuns DROP CONSTRAINT IF EXISTS albuns_pkey;
ALTER TABLE albuns ALTER COLUMN id TYPE VARCHAR(255);
ALTER TABLE albuns ADD PRIMARY KEY (id);

-- 3. Regionais (Mantém ID como Integer/BigSerial conforme requisito)
-- Não altera para VARCHAR

-- 4. Atualizar tabela de relacionamento artista_album para acompanhar mudança de tipo
ALTER TABLE artista_album ALTER COLUMN artista_id TYPE VARCHAR(255);
ALTER TABLE artista_album ALTER COLUMN album_id TYPE VARCHAR(255);

-- Recriar FKs
ALTER TABLE artista_album ADD CONSTRAINT artista_album_artista_id_fkey FOREIGN KEY (artista_id) REFERENCES artistas(id) ON DELETE CASCADE;
ALTER TABLE artista_album ADD CONSTRAINT artista_album_album_id_fkey FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE;

-- 5. Outros campos
ALTER TABLE albuns ADD COLUMN IF NOT EXISTS url_capa VARCHAR(500);

ALTER TABLE regionais ADD COLUMN IF NOT EXISTS id_externo INTEGER;
CREATE INDEX IF NOT EXISTS idx_regionais_id_externo ON regionais(id_externo);
