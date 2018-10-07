package com.task_management_system.endpoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleResponseDto {

    @NotNull
    private String id;

    @NotNull
    private String roleName;

}
