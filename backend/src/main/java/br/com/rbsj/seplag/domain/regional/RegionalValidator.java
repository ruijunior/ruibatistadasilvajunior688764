package br.com.rbsj.seplag.domain.regional;

import br.com.rbsj.seplag.domain.validation.Error;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

class RegionalValidator {

    private final Regional regional;
    private final ValidationHandler handler;

    public RegionalValidator(Regional regional, ValidationHandler handler) {
        this.regional = regional;
        this.handler = handler;
    }

    public void validate() {
        checkNome();
    }

    private void checkNome() {
        String nome = regional.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            handler.append(new Error("Nome da regional é obrigatório"));
        }
        if (nome != null && nome.length() > 200) {
            handler.append(new Error("Nome da regional não pode ter mais de 200 caracteres"));
        }
    }
}
