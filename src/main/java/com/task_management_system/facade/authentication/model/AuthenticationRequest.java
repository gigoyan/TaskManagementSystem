package com.task_management_system.facade.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest{
    private String username;
    private String plainPassword;
    private boolean rememberMe;
}
