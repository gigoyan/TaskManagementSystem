package com.task_management_system.endpoint.component;

import com.task_management_system.endpoint.model.user.UserCreationDto;
import com.task_management_system.endpoint.model.user.UserModificationDto;
import com.task_management_system.endpoint.model.user.UserResponseDto;
import com.task_management_system.entity.User;
import com.task_management_system.service.model.userRequsts.UserCreationRequest;
import com.task_management_system.service.model.userRequsts.UserModificationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserCreationRequest convertCreationDtoToRequest(final UserCreationDto userCreationDto){
        final String name = userCreationDto.getName();
        final String userName = userCreationDto.getUserName();
        final String password = userCreationDto.getPassword();
        final String roleId = userCreationDto.getRoleId();
        final UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setName(name);
        userCreationRequest.setUserName(userName);
        userCreationRequest.setPassword(password);
        userCreationRequest.setRoleId(roleId);
        return userCreationRequest;
    }

    public UserModificationRequest convertModificationDtoToRequest(final UserModificationDto userModificationDto){
        final String id = userModificationDto.getId();
        final String name = userModificationDto.getName();
        final String userName = userModificationDto.getUserName();
        final String password = userModificationDto.getPassword();
        final UserModificationRequest userModificationRequest = new UserModificationRequest();
        userModificationRequest.setId(id);
        userModificationRequest.setName(name);
        userModificationRequest.setUserName(userName);
        userModificationRequest.setPassword(password);
        return userModificationRequest;
    }

    public UserResponseDto convertEntityToDto(final User user){
        final String name = user.getName();
        final String id = user.getId();
        final UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(name);
        userResponseDto.setId(id);
        return userResponseDto;
    }

    public List<UserResponseDto> convertEntityToDtoList(final List<User> users){
        final List<UserResponseDto> userResponseDto = new ArrayList<>();
        for (User user: users) {
            userResponseDto.add(convertEntityToDto(user));
        }
        return userResponseDto;
    }
}
