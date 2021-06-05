package com.training.periodical.model.dao;

public class DaoException extends Exception {
    private static final long serialVersionUID = -8772400255500503126L;

    public DaoException() {
        super();
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    public DaoException(Throwable cause) {
        super(cause);
    }
}
