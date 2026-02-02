package br.com.rbsj.seplag.domain.validation;

import java.util.List;

public class DomainException extends RuntimeException {

    private final List<Error> errors;

    public DomainException(List<Error> errors) {
        super(errors.isEmpty() ? "" : errors.get(0).message());
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
