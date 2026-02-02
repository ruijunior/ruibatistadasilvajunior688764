CREATE TABLE artista_album (
    artista_id BIGINT NOT NULL,
    album_id BIGINT NOT NULL,
    PRIMARY KEY (artista_id, album_id),
    FOREIGN KEY (artista_id) REFERENCES artistas(id) ON DELETE CASCADE,
    FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE
);

CREATE INDEX idx_artista_album_artista ON artista_album(artista_id);
CREATE INDEX idx_artista_album_album ON artista_album(album_id);
