package com.task_management_system.endpoint.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreationDto {

    @NotNull
    private String name;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String roleId;
}
