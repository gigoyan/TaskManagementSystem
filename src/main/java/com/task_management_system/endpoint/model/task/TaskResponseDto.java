package com.task_management_system.endpoint.model.task;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskResponseDto {

    @NotNull
    private String id;

    @NotNull
    private String story;

    @NotNull
    private String description;

    @NotNull
    private String projectId;

    private String userId;
}
