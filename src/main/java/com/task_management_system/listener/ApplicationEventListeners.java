package com.task_management_system.listener;

import com.google.common.collect.Lists;
import com.task_management_system.entity.*;
import com.task_management_system.misc.RoleType;
import com.task_management_system.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ApplicationEventListeners {

    private final Logger logger = LoggerFactory.getLogger(ApplicationEventListeners.class);

    private static boolean DATA_LOADED = false;

    // region VALUES

    @Value("${application.adminName}")
    private String adminName;

    @Value("${application.adminUserName}")
    private String adminUserName;

    @Value("${application.adminPassword}")
    private String adminPassword;

    // endregion

    @Autowired
    private AbstractRepository abstractRepository;

    @EventListener({ContextRefreshedEvent.class})
        public void onContextRefreshedEvent() {
            if(DATA_LOADED){
                return;
            }
            logger.info("Saving Roles ...");
            final Role roleAdmin = StaticData.createRole(RoleType.ROLE_ADMIN);
            final Role roleUser = StaticData.createRole(RoleType.ROLE_USER);
            abstractRepository.saveAll(Lists.newArrayList(roleAdmin, roleUser));
            logger.debug("Done saving Roles.");

            Set<Role> adminRolesSet = new HashSet<>();
            adminRolesSet.add(roleAdmin);
            Set<Role> userRolesSet = new HashSet<>();
            userRolesSet.add(roleUser);

            logger.info("Saving Admin and other users...");
            final User admin = StaticData.createUser(adminName);
            final User user1 = StaticData.createUser("User 1");
            final User user2 = StaticData.createUser("User 2");
            abstractRepository.saveAll(Lists.newArrayList(user1, user2, admin));

            final APIUserDetail apiAdminDetail = StaticData.createUserDetail(adminRolesSet, adminUserName, adminPassword, admin);
            final APIUserDetail apiUserDetail = StaticData.createUserDetail(userRolesSet, "userName1", "user1pass", user1);
            final APIUserDetail apiUserDetail2 = StaticData.createUserDetail(userRolesSet, "UserName2", "user2pass", user2);
            abstractRepository.saveAll(Lists.newArrayList(apiUserDetail, apiUserDetail2, apiAdminDetail));
            logger.debug("Done saving other users.");

            logger.info("Saving Projects...");
            final Project project1 = StaticData.createProject("Project 1", "Description for project 1");
            final Project project2 = StaticData.createProject("Project 2", "Description for project 2");
            final Project project3 = StaticData.createProject("Project 3", "Description for project 3");
            abstractRepository.saveAll(Lists.newArrayList(project1, project2, project3));
            logger.debug("Done saving projects.");

            logger.info("Saving Tasks...");
            final Task task1 = StaticData.createTask("1 task story", "1 task description", project1, user1);
            final Task task2 = StaticData.createTask("2 task story", "2 task description", project2, user1);
            final Task task3 = StaticData.createTask("3 task story", "3 task description", project2, user1);
            final Task task4 = StaticData.createTask("4 task story", "4 task description", project3, user1);
            final Task task5 = StaticData.createTask("5 task story", "5 task description", project3, user1);
            final Task task6 = StaticData.createTask("6 task story", "6 task description", project3, user1);
            final Task task7 = StaticData.createTask("7 task story", "7 task description", project2, user2);
            final Task task8 = StaticData.createTask("8 task story", "8 task description", project1, user2);
            final Task task9 = StaticData.createTask("9 task story", "9 task description", project1, user2);
            abstractRepository.saveAll(Lists.newArrayList(task1, task2, task3, task4, task5, task6, task7, task8, task9));
            logger.debug("Done saving tasks.");

            DATA_LOADED = true;
        }
}
