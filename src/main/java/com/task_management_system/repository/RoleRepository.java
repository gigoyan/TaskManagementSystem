package com.task_management_system.repository;

import com.task_management_system.entity.Role;
import com.task_management_system.misc.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByType(RoleType roleType);
}
