package com.training.periodical.util.validator;

public class ValidatorException extends Exception{
    private static final long serialVersionUID = 7335711383291322546L;

    public ValidatorException() {
        super();
    }
    public ValidatorException(String message) {
        super(message);
    }
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
