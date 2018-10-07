package com.task_management_system.service.impl;

import com.task_management_system.entity.Project;
import com.task_management_system.repository.ProjectRepository;
import com.task_management_system.service.ProjectService;
import com.task_management_system.service.exception.EntityNotFoundException;
import com.task_management_system.service.model.projectRequsets.ProjectCreationRequest;
import com.task_management_system.service.model.projectRequsets.ProjectModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.springframework.util.Assert.notNull;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public Project create(final ProjectCreationRequest projectCreationRequest) {
        notNull(projectCreationRequest, "project creation request can not be null");

        final String name = projectCreationRequest.getName();
        final String description = projectCreationRequest.getDescription();
        validate(name, description);

        final Project newProject = new Project();
        newProject.setName(name);
        newProject.setDescription(description);

        return projectRepository.save(newProject);
    }

    @Override
    public Project get(String id) {
        notEmpty(id, "Project id can not be empty");
        final Project project = projectRepository.findById(id).orElse(null);
        if (project == null) throw new EntityNotFoundException();
        return project;
    }

    @Override
    public Project update(ProjectModificationRequest projectModificationRequest) {
        notNull(projectModificationRequest, "project modification request can not be null");
        final String id = projectModificationRequest.getId();
        notEmpty(id, "id can not be empty");

        final Project existingProject = get(id);

        final String name = projectModificationRequest.getName();
        final String description = projectModificationRequest.getDescription();

        if (!name.isEmpty()) existingProject.setName(name);
        if (!description.isEmpty()) existingProject.setDescription(description);
        return projectRepository.save(existingProject);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    private void validate(String name, String description) {
        notEmpty(name, "project name can not be empty");
        notEmpty(description, "project description can not be empty");
    }
}
