package com.task_management_system.service.impl;

import com.task_management_system.entity.Project;
import com.task_management_system.entity.Task;
import com.task_management_system.repository.TaskRepository;
import com.task_management_system.service.TaskService;
import com.task_management_system.service.exception.EntityNotFoundException;
import com.task_management_system.service.exception.NotAuthorizedUserException;
import com.task_management_system.service.model.taskRequests.TaskCreationRequest;
import com.task_management_system.service.model.taskRequests.TaskModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.springframework.util.Assert.notNull;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Task get(String id) {
        notNull(id, "id can not be null");
        final Task task = taskRepository.findById(id).orElse(null);
        if (task == null) throw new EntityNotFoundException();
        return task;
    }

    @Override
    public Task create(TaskCreationRequest taskCreationRequest) {
        notNull(taskCreationRequest, "task creation request can not be null");
        final String story = taskCreationRequest.getStory();
        final String description = taskCreationRequest.getDescription();
        final String userId = taskCreationRequest.getUserId();
        final String projectId = taskCreationRequest.getProjectId();
        validate(story, description, projectId);

        final Project project = projectService.get(projectId);
        final Task newTask = new Task();
        newTask.setStory(story);
        newTask.setDescription(description);
        newTask.setProject(project);
        if (userId != null){
            newTask.setUser(userService.get(userId));
        }
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateByAdmin(TaskModificationRequest taskModificationRequest) {
        final Task existingTask = updateDefault(taskModificationRequest);

        final String projectId = taskModificationRequest.getProjectId();
        if (!projectId.isEmpty()) existingTask.setProject(projectService.get(projectId));
        return taskRepository.save(existingTask);
    }

    @Override
    public Task update(TaskModificationRequest taskModificationRequest, String userId) {
        notEmpty(userId, "id can not be empty");
        final String userIdOfExistingTask = get(taskModificationRequest.getId()).getUser().getId();
        if (!userId.equals(userIdOfExistingTask)) throw new NotAuthorizedUserException("Current user doesn't have authorisation for updating this task");
        return taskRepository.save(updateDefault(taskModificationRequest));
    }

    private Task updateDefault(TaskModificationRequest taskModificationRequest){
        notNull(taskModificationRequest, "task modification request can not be null");
        final String id = taskModificationRequest.getId();
        notEmpty(id, "id can not be empty");
        final Task existingTask = get(id);

        final String story = taskModificationRequest.getStory();
        final String description = taskModificationRequest.getDescription();
        final String userId = taskModificationRequest.getUserId();
        if (!story.isEmpty()) existingTask.setStory(story);
        if (!description.isEmpty()) existingTask.setDescription(description);
        if (!userId.isEmpty()) existingTask.setUser(userService.get(userId));
        return existingTask;
    }

    @Override
    public Map<String, List<Task>> getAll() {
        final List<Task> tasks = taskRepository.findAll();
        return mappingTasks(tasks);
    }

    @Override
    public Map<String, List<Task>> getAllByUser(String userId) {
        notEmpty(userId, "user request id can not be null");

        final List<Task> tasks = taskRepository.findAllByUser_Id(userId);
        return mappingTasks(tasks);
    }

    private Map<String, List<Task>> mappingTasks(List<Task> tasks){
        final Set<Project> projects = new HashSet<>();
        for (Task task:tasks) {
            projects.add(task.getProject());
        }
        final Map<String, List<Task>> tasksByProject = new HashMap<>();
        for (Project project:projects) {
            ArrayList<Task> tempTasks = new ArrayList<>();
            String projectName = project.getName();
            for (Task t:tasks) {
                if (t.getProject().getId().equals(project.getId())){
                    tempTasks.add(t);
                }
            }
            tasksByProject.put(projectName, tempTasks);
        }
        return tasksByProject;
    }

    private void validate(String story, String description, String projectId){
        notEmpty(story, "task story can not be empty");
        notEmpty(description, "task description can not be empty");
        notEmpty(projectId, "project id can not be empty");
    }
}
