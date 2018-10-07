package com.task_management_system.facade.authentication.exception;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;

public class AuthException extends AuthenticationException {

    private final int httpStatusCode = HttpServletResponse.SC_UNAUTHORIZED;

    public AuthException(String msg) {
        this(msg, null);
    }

    public AuthException(Exception cause) {
        this(null, cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
