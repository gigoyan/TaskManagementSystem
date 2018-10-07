package com.task_management_system.endpoint.model.project;

import com.task_management_system.endpoint.model.task.TaskResponseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class ProjectResponseDto {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Set<TaskResponseDto> tasks;

}
