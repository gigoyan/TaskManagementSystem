package com.task_management_system.endpoint.component;

import com.task_management_system.endpoint.model.project.ProjectCreationDto;
import com.task_management_system.endpoint.model.project.ProjectModificationDto;
import com.task_management_system.endpoint.model.project.ProjectResponseDto;
import com.task_management_system.endpoint.model.task.TaskResponseDto;
import com.task_management_system.entity.Project;
import com.task_management_system.service.model.projectRequsets.ProjectCreationRequest;
import com.task_management_system.service.model.projectRequsets.ProjectModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ProjectConverter {

    @Autowired
    private TaskConverter taskConverter;

    public ProjectCreationRequest convertCreationDtoToRequest(final ProjectCreationDto projectCreationDto){
        final String name = projectCreationDto.getName();
        final String description = projectCreationDto.getDescription();
        final ProjectCreationRequest projectCreationRequest = new ProjectCreationRequest();

        projectCreationRequest.setName(name);
        projectCreationRequest.setDescription(description);
        return projectCreationRequest;
    }

    public ProjectModificationRequest convertModificationDtoToRequest(final ProjectModificationDto projectModificationDto){
        final String id = projectModificationDto.getId();
        final String name = projectModificationDto.getName();
        final String description = projectModificationDto.getDescription();
        final ProjectModificationRequest projectModificationRequest = new ProjectModificationRequest();

        projectModificationRequest.setId(id);
        projectModificationRequest.setName(name);
        projectModificationRequest.setDescription(description);
        return projectModificationRequest;
    }

    public ProjectResponseDto convertEntityToDto(final Project project){
        final String name = project.getName();
        final String description = project.getDescription();
        final String id = project.getId();
        final Set<TaskResponseDto> tasks = taskConverter.convertEntityToDtoSet(project.getTasks());
        final ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        projectResponseDto.setId(id);
        projectResponseDto.setName(name);
        projectResponseDto.setDescription(description);
        projectResponseDto.setTasks(tasks);
        return projectResponseDto;
    }

    public List<ProjectResponseDto> convertEntityToDtoList(final List<Project> projects){
        final List<ProjectResponseDto> projectResponseDto = new ArrayList<>();
        for (Project project: projects) {
            projectResponseDto.add(convertEntityToDto(project));
        }
        return projectResponseDto;
    }
}
