package com.task_management_system.service.impl;

import com.task_management_system.entity.Role;
import com.task_management_system.misc.RoleType;
import com.task_management_system.repository.RoleRepository;
import com.task_management_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role get(final String roleId) {
        notEmpty(roleId, "role id can not be empty");
        return roleRepository.findById(roleId).orElse(null);
    }

    @Override
    public Role getByRoleType(RoleType roleType) {
        notNull(roleType, "role type can not be null");
        return roleRepository.findByType(roleType);
    }

    @Override
    public List<Role> findAll() {
        final List<Role> roles = roleRepository.findAll();
        notEmpty(roles, "There is no role created");
        return roles;
    }
}
