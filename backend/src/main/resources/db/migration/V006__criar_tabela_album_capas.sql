CREATE TABLE album_capas (
    album_id VARCHAR(36) NOT NULL,
    url_capa VARCHAR(500) NOT NULL,
    CONSTRAINT fk_album_capas_album FOREIGN KEY (album_id) REFERENCES albuns(id)
);

CREATE INDEX idx_album_capas_album_id ON album_capas(album_id);

ALTER TABLE albuns DROP COLUMN url_capa;
