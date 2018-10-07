package com.task_management_system.endpoint.model.task;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskModificationDto {

    @NotNull
    private String id;

    private String story;

    private String description;

    private String projectId;

    private String userId;
}
