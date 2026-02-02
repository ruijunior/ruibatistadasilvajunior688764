package br.com.rbsj.seplag.domain.validation;

public abstract class ValidationHandler {

    public abstract ValidationHandler append(Error error);

    public abstract ValidationHandler append(ValidationHandler handler);

    public abstract ValidationHandler validate(Validation validation);

    public abstract boolean hasErrors();

    public interface Validation {
        void validate();
    }
}
