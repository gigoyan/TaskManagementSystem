package com.task_management_system.service.exception;

public class NotAuthorizedUserException extends RuntimeException {
    public NotAuthorizedUserException(String s) {
        super(s);
    }
}
