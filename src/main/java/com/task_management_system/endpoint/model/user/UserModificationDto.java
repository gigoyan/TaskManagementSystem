package com.task_management_system.endpoint.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserModificationDto {

    @NotNull
    private String id;

    private String name;

    private String userName;

    private String password;
}
