package com.task_management_system.service;

import com.task_management_system.entity.Task;
import com.task_management_system.service.model.taskRequests.TaskCreationRequest;
import com.task_management_system.service.model.taskRequests.TaskModificationRequest;

import java.util.List;
import java.util.Map;

public interface TaskService {

    Task create(TaskCreationRequest taskCreationRequest);

    Task updateByAdmin(TaskModificationRequest taskModificationRequest);

    Task update(TaskModificationRequest taskModificationRequest, String userId);

    Map<String, List<Task>> getAll();

    Map<String, List<Task>> getAllByUser(String userId);

    Task get(String id);
}
