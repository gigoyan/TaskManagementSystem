package com.task_management_system.endpoint.component;

import com.task_management_system.endpoint.model.task.TaskCreationDto;
import com.task_management_system.endpoint.model.task.TaskModificationDto;
import com.task_management_system.endpoint.model.task.TaskResponseDto;
import com.task_management_system.entity.Task;
import com.task_management_system.service.model.taskRequests.TaskCreationRequest;
import com.task_management_system.service.model.taskRequests.TaskModificationRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskConverter {

    public TaskCreationRequest convertCreationDtoToRequest(final TaskCreationDto taskCreationDto){
        final String story = taskCreationDto.getStory();
        final String description = taskCreationDto.getDescription();
        final String projectId = taskCreationDto.getProjectId();
        final String userId = taskCreationDto.getUserId();

        final TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setStory(story);
        taskCreationRequest.setDescription(description);
        taskCreationRequest.setProjectId(projectId);
        taskCreationRequest.setUserId(userId);

        return taskCreationRequest;
    }

    public TaskModificationRequest convertModificationDtoToRequest(TaskModificationDto taskModificationDto){
        final String id = taskModificationDto.getId();
        final String story = taskModificationDto.getStory();
        final String description = taskModificationDto.getDescription();
        final String projectId = taskModificationDto.getProjectId();
        final String userId = taskModificationDto.getUserId();

        final TaskModificationRequest taskModificationRequest = new TaskModificationRequest();
        taskModificationRequest.setId(id);
        taskModificationRequest.setStory(story);
        taskModificationRequest.setDescription(description);
        taskModificationRequest.setProjectId(projectId);
        taskModificationRequest.setUserId(userId);

        return taskModificationRequest;
    }

    public TaskResponseDto convertEntityToDto(Task task){
        final String id = task.getId();
        final String projectId = task.getProject().getId();
        final String story = task.getStory();
        final String description = task.getDescription();
        final TaskResponseDto taskResponseDto = new TaskResponseDto();

        taskResponseDto.setId(id);
        taskResponseDto.setProjectId(projectId);
        taskResponseDto.setStory(story);
        taskResponseDto.setDescription(description);
        if (task.getUser() != null) {
            final String userId = task.getUser().getId();
            taskResponseDto.setUserId(userId);
        }

        return taskResponseDto;
    }

    public List<TaskResponseDto> convertEntityToDtoList(List<Task> tasks){
        final List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        for (Task task:tasks) {
            taskResponseDtoList.add(convertEntityToDto(task));
        }
        return taskResponseDtoList;
    }

    public Set<TaskResponseDto> convertEntityToDtoSet(Set<Task> tasks){
        Set<TaskResponseDto> taskResponseDtoSet = new HashSet<>();
        if (tasks != null) {
            for (Task task : tasks) {
                taskResponseDtoSet.add(convertEntityToDto(task));
            }
        }
        return taskResponseDtoSet;
    }

    public Map<String, List<TaskResponseDto>> convertEntityToDtoMap(Map<String, List<Task>> tasks){
        final Map<String, List<TaskResponseDto>> taskDtoMap = new HashMap<>();
        final Set<String> iterator = tasks.keySet();
        for (String key:iterator) {
            taskDtoMap.put(key, convertEntityToDtoList(tasks.get(key)));
        }
        return taskDtoMap;
    }
}
