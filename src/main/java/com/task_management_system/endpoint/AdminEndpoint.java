package com.task_management_system.endpoint;

import com.task_management_system.endpoint.component.ProjectConverter;
import com.task_management_system.endpoint.component.RoleConverter;
import com.task_management_system.endpoint.component.TaskConverter;
import com.task_management_system.endpoint.component.UserConverter;
import com.task_management_system.endpoint.model.RoleResponseDto;
import com.task_management_system.endpoint.model.project.ProjectCreationDto;
import com.task_management_system.endpoint.model.project.ProjectModificationDto;
import com.task_management_system.endpoint.model.project.ProjectResponseDto;
import com.task_management_system.endpoint.model.task.TaskCreationDto;
import com.task_management_system.endpoint.model.task.TaskModificationDto;
import com.task_management_system.endpoint.model.task.TaskResponseDto;
import com.task_management_system.endpoint.model.user.UserCreationDto;
import com.task_management_system.endpoint.model.user.UserModificationDto;
import com.task_management_system.endpoint.model.user.UserResponseDto;
import com.task_management_system.entity.Project;
import com.task_management_system.entity.Role;
import com.task_management_system.entity.Task;
import com.task_management_system.entity.User;
import com.task_management_system.service.ProjectService;
import com.task_management_system.service.RoleService;
import com.task_management_system.service.TaskService;
import com.task_management_system.service.UserService;
import com.task_management_system.service.model.projectRequsets.ProjectCreationRequest;
import com.task_management_system.service.model.projectRequsets.ProjectModificationRequest;
import com.task_management_system.service.model.taskRequests.TaskCreationRequest;
import com.task_management_system.service.model.taskRequests.TaskModificationRequest;
import com.task_management_system.service.model.userRequsts.UserCreationRequest;
import com.task_management_system.service.model.userRequsts.UserModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminEndpoint {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectConverter projectConverter;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private RoleService roleService;


    // region USERS

    @RequestMapping(method = RequestMethod.GET, value = "users")
    public List<UserResponseDto> getAllUsers(){
        final List<User> users = userService.getAllByAdmin();
        return userConverter.convertEntityToDtoList(users);
    }

    @RequestMapping(method = RequestMethod.POST, value = "users")
    public UserResponseDto createUser(@RequestBody final UserCreationDto userCreationDto){
        final UserCreationRequest userCreationRequest = userConverter.convertCreationDtoToRequest(userCreationDto);
        final User createdUser = userService.create(userCreationRequest);
        return userConverter.convertEntityToDto(createdUser);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "users")
    public UserResponseDto updateUser(@RequestBody final UserModificationDto userModificationDto){
        final UserModificationRequest userModificationRequest = userConverter.convertModificationDtoToRequest(userModificationDto);
        final User updatedUser = userService.update(userModificationRequest);
        return userConverter.convertEntityToDto(updatedUser);
    }

    // endregion

    // region PROJECTS

    @RequestMapping(method = RequestMethod.POST, value = "projects")
    public ProjectResponseDto createProject(@RequestBody final ProjectCreationDto projectDto){
        final ProjectCreationRequest projectCreationRequest = projectConverter.convertCreationDtoToRequest(projectDto);
        final Project createdProject = projectService.create(projectCreationRequest);
        return projectConverter.convertEntityToDto(createdProject);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "projects")
    public ProjectResponseDto updateProject(@RequestBody final ProjectModificationDto projectDto){
        final ProjectModificationRequest projectModificationRequest = projectConverter.convertModificationDtoToRequest(projectDto);
        final Project updatedProject = projectService.update(projectModificationRequest);
        return projectConverter.convertEntityToDto(updatedProject);
    }

    @RequestMapping(method = RequestMethod.GET, value = "projects/{id}")
    public ProjectResponseDto getProject(@PathVariable String id){
        final Project receivedProject = projectService.get(id);
        return projectConverter.convertEntityToDto(receivedProject);
    }

    @RequestMapping(method = RequestMethod.GET, value = "projects")
    public List<ProjectResponseDto> getAllProjects(){
        final List<Project> receivedProjects = projectService.findAll();
        return projectConverter.convertEntityToDtoList(receivedProjects);
    }

    // endregion

    // region TASKS

    @RequestMapping(method = RequestMethod.GET, value = "tasks")
    public Map<String, List<TaskResponseDto>> getAllTasks(){
        final Map<String, List<Task>> tasks = taskService.getAll();
        return taskConverter.convertEntityToDtoMap(tasks);
    }

    @RequestMapping(method = RequestMethod.POST, value = "tasks")
    public TaskResponseDto createTask(@RequestBody final TaskCreationDto taskCreationDto){
        final TaskCreationRequest taskCreationRequest = taskConverter.convertCreationDtoToRequest(taskCreationDto);
        final Task createdTask = taskService.create(taskCreationRequest);
        return taskConverter.convertEntityToDto(createdTask);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "tasks")
    public TaskResponseDto updateTask(@RequestBody final TaskModificationDto taskModificationDto){
        final TaskModificationRequest taskModificationRequest = taskConverter.convertModificationDtoToRequest(taskModificationDto);
        final Task updatedTask  = taskService.updateByAdmin(taskModificationRequest);
        return taskConverter.convertEntityToDto(updatedTask);
    }

    @RequestMapping(method = RequestMethod.GET, value = "tasks/{userId}")
    public Map<String, List<TaskResponseDto>> getAllTasksByUser(@PathVariable final String userId){
        final Map<String, List<Task>> tasks = taskService.getAllByUser(userId);
        return taskConverter.convertEntityToDtoMap(tasks);
    }

    // endregion

    // region ROLES

    @RequestMapping(method = RequestMethod.GET, value = "roles")
    public List<RoleResponseDto> getAllRoles(){
        final List<Role> roles = roleService.findAll();
        return roleConverter.convertEntityToDtoList(roles);
    }

    // endregion


}
