package br.com.rbsj.seplag.domain.album;

import br.com.rbsj.seplag.domain.validation.Error;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

class AlbumValidator {

    private final Album album;
    private final ValidationHandler handler;

    public AlbumValidator(Album album, ValidationHandler handler) {
        this.album = album;
        this.handler = handler;
    }

    public void validate() {
        checkTitulo();
    }

    private void checkTitulo() {
        String titulo = album.getTitulo();
        if (titulo == null || titulo.trim().isEmpty()) {
            handler.append(new Error("Título do álbum é obrigatório"));
        }
        if (titulo != null && titulo.length() > 200) {
            handler.append(new Error("Título do álbum não pode ter mais de 200 caracteres"));
        }
    }
}
