package com.task_management_system.service.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("There is no such entity");
    }
}
