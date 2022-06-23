package com.federicobonel.mapcoloring.exceptions;

public class InvalidQueryFormat extends RuntimeException {
    public InvalidQueryFormat() {
        super();
    }

    public InvalidQueryFormat(String message) {
        super(message);
    }

    public InvalidQueryFormat(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryFormat(Throwable cause) {
        super(cause);
    }

    protected InvalidQueryFormat(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
