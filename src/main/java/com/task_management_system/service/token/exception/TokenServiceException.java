package com.task_management_system.service.token.exception;

public class TokenServiceException extends Exception{

    public TokenServiceException() {
    }

    public TokenServiceException(String message) {
        super(message);
    }

    public TokenServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenServiceException(Throwable cause) {
        super(cause);
    }

    public TokenServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
