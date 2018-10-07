package com.task_management_system.service;

import com.task_management_system.entity.User;
import com.task_management_system.service.model.userRequsts.UserCreationRequest;
import com.task_management_system.service.model.userRequsts.UserModificationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User create(UserCreationRequest userCreationRequest);

    User update(UserModificationRequest userModificationRequest);

    User get(String id);

    List<User> getAll();

}
