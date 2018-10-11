package com.task_management_system.service;

import com.task_management_system.entity.Role;
import com.task_management_system.misc.RoleType;

import java.util.List;

public interface RoleService {

    Role get(String roleId);

    Role getByRoleType(RoleType roleType);

    List<Role> findAll();
}
