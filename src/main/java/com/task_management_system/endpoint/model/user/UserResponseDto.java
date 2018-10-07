package com.task_management_system.endpoint.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserResponseDto {

    @NotNull
    private String id;

    @NotNull
    private String name;
}
