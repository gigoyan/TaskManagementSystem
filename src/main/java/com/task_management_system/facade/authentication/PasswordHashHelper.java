package com.task_management_system.facade.authentication;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHashHelper {

    private static final int LOG_ROUNDS = 12;

    public static Boolean isPasswordCorrect(final String plainPassword) {
        return BCrypt.checkpw(plainPassword, hashPassword(plainPassword));
    }

    public static String hashPassword(final String plainPassword) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        return BCrypt.hashpw(plainPassword, salt);
    }
}
