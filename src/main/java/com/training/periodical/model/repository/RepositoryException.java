package com.training.periodical.model.repository;


/**
 * Exception for repository layer
 *
 */
public class RepositoryException extends Exception {
    private static final long serialVersionUID = 2970936039964677068L;

    public RepositoryException() {
        super();
    }
    public RepositoryException(String message) {
        super(message);
    }
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
