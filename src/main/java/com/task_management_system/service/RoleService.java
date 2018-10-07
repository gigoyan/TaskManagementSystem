package com.task_management_system.service;

import com.task_management_system.entity.Role;

import java.util.List;

public interface RoleService {

    Role get(String roleId);

    List<Role> findAll();
}
