package com.task_management_system.service;

import com.task_management_system.entity.Project;
import com.task_management_system.service.model.projectRequsets.ProjectCreationRequest;
import com.task_management_system.service.model.projectRequsets.ProjectModificationRequest;

import java.util.List;

public interface ProjectService {
    Project create(ProjectCreationRequest projectCreationRequest);

    Project get(String id);

    Project update(ProjectModificationRequest projectModificationRequest);

    List<Project> findAll();
}
