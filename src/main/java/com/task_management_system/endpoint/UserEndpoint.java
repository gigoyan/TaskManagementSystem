package com.task_management_system.endpoint;

import com.task_management_system.endpoint.component.TaskConverter;
import com.task_management_system.endpoint.component.UserConverter;
import com.task_management_system.endpoint.model.task.TaskModificationDto;
import com.task_management_system.endpoint.model.task.TaskResponseDto;
import com.task_management_system.endpoint.model.user.UserModificationDto;
import com.task_management_system.endpoint.model.user.UserResponseDto;
import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.entity.Task;
import com.task_management_system.entity.User;
import com.task_management_system.service.TaskService;
import com.task_management_system.service.UserService;
import com.task_management_system.service.model.taskRequests.TaskModificationRequest;
import com.task_management_system.service.model.userRequsts.UserModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/user")
public class UserEndpoint {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private TaskService taskService;

    // region USERS

    @RequestMapping(method = RequestMethod.GET, value = "users")
    public UserResponseDto getUser(){
        final APIUserDetail apiUserDetail = (APIUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(apiUserDetail.getUser().getId());
        userResponseDto.setName(apiUserDetail.getUser().getName());
        return userResponseDto;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "users")
    public UserResponseDto updateUser(@RequestBody final UserModificationDto userModificationDto){
        final APIUserDetail apiUserDetail = (APIUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userModificationDto.setId(apiUserDetail.getUser().getId());
        final UserModificationRequest userModificationRequest = userConverter.convertModificationDtoToRequest(userModificationDto);
        final User updatedUser = userService.update(userModificationRequest);
        return userConverter.convertEntityToDto(updatedUser);
    }

    //endregion

    // region TASKS

    @RequestMapping(method = RequestMethod.GET, value = "tasks")
    public Map<String, List<TaskResponseDto>> getAllTasks(){
        final APIUserDetail apiUserDetail = (APIUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = apiUserDetail.getUser().getId();
        final Map<String, List<Task>> tasks = taskService.getAllByUser(userId);
        return taskConverter.convertEntityToDtoMap(tasks);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "tasks")
    public TaskResponseDto updateTask(@RequestBody final TaskModificationDto taskModificationDto){
        final APIUserDetail apiUserDetail = (APIUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = apiUserDetail.getUser().getId();
        final TaskModificationRequest taskModificationRequest = taskConverter.convertModificationDtoToRequest(taskModificationDto);
        final Task updatedTask = taskService.update(taskModificationRequest, userId);
        return taskConverter.convertEntityToDto(updatedTask);
    }

    // endregion
}
