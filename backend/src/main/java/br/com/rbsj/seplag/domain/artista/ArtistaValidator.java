package br.com.rbsj.seplag.domain.artista;

import br.com.rbsj.seplag.domain.validation.Error;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

class ArtistaValidator {

    private final Artista artista;
    private final ValidationHandler handler;

    public ArtistaValidator(Artista artista, ValidationHandler handler) {
        this.artista = artista;
        this.handler = handler;
    }

    public void validate() {
        checkNome();
        checkTipo();
    }

    private void checkNome() {
        String nome = artista.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            handler.append(new Error("Nome do artista é obrigatório"));
        }
        if (nome != null && nome.length() > 200) {
            handler.append(new Error("Nome do artista não pode ter mais de 200 caracteres"));
        }
    }

    private void checkTipo() {
        if (artista.getTipo() == null) {
            handler.append(new Error("Tipo do artista é obrigatório"));
        }
    }
}
