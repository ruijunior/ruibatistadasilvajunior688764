package br.com.rbsj.seplag.domain.validation;

import java.util.ArrayList;
import java.util.List;

public class ThrowsValidationHandler extends ValidationHandler {

    @Override
    public ValidationHandler append(Error error) {
        throw new DomainException(List.of(error));
    }

    @Override
    public ValidationHandler append(ValidationHandler handler) {
        return this;
    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (Exception e) {
            throw new DomainException(List.of(new Error(e.getMessage())));
        }
        return this;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}
