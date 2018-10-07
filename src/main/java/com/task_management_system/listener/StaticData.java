package com.task_management_system.listener;

import com.task_management_system.entity.*;
import com.task_management_system.misc.RoleType;

import java.util.Set;

public class StaticData {

    public static Role createRole(RoleType roleType) {
        final Role role = new Role();
        role.setType(roleType);
        return role;
    }

    public static User createUser(String name){
        final User user = new User();
        user.setName(name);
        return user;
    }

    public static APIUserDetail createUserDetail(Set<Role> roleSet, String userName, String password, User user){
        final APIUserDetail apiUserDetail = new APIUserDetail();
        apiUserDetail.setUsername(userName);
        apiUserDetail.setPasswordHash(password);
        apiUserDetail.setApproved(true);
        apiUserDetail.setEnabled(true);
        apiUserDetail.setUser(user);
        apiUserDetail.setRoles(roleSet);
        return apiUserDetail;
    }

    public static Project createProject(String name, String description){
        final Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        return project;
    }

    public static Task createTask(String story, String description, Project project, User user){
        final Task task = new Task();
        task.setStory(story);
        task.setDescription(description);
        task.setProject(project);
        task.setUser(user);
        return task;
    }
}
