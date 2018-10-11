package com.task_management_system.service.impl;

import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.entity.Role;
import com.task_management_system.entity.User;
import com.task_management_system.misc.RoleType;
import com.task_management_system.repository.APIUserDetailRepository;
import com.task_management_system.repository.UserRepository;
import com.task_management_system.service.RoleService;
import com.task_management_system.service.UserService;
import com.task_management_system.service.exception.EntityNotFoundException;
import com.task_management_system.service.model.userRequsts.UserCreationRequest;
import com.task_management_system.service.model.userRequsts.UserModificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.springframework.util.Assert.notNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private APIUserDetailRepository apiUserDetailRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User create(final UserCreationRequest userCreationRequest) {
        notNull(userCreationRequest, "userCreationRequest can not be null");
        final String name = userCreationRequest.getName();
        final String userName = userCreationRequest.getUserName();
        final String password = userCreationRequest.getPassword();
        final String roleId = userCreationRequest.getRoleId();
        validate(name, userName, password, roleId);

        final Role newRole = roleService.get(roleId);

        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(newRole);
        User newUser = new User();
        newUser.setName(name);
        newUser = userRepository.save(newUser);

        final APIUserDetail apiUserDetail = new APIUserDetail();

        apiUserDetail.setUsername(userName);
        apiUserDetail.setPasswordHash(password);
        apiUserDetail.setApproved(true);
        apiUserDetail.setEnabled(true);
        apiUserDetail.setRoles(rolesSet);
        apiUserDetail.setUser(newUser);
        apiUserDetailRepository.save(apiUserDetail);
        return newUser;
    }

    @Override
    public User update(UserModificationRequest userModificationRequest) {
        notNull(userModificationRequest, "userCreationDto can not be null");
        final String id = userModificationRequest.getId();
        User existingUser = get(id);

        final String name = userModificationRequest.getName();
        final String userName = userModificationRequest.getUserName();
        final String password = userModificationRequest.getPassword();

        if (!name.isEmpty()){
            existingUser.setName(name);
            existingUser = userRepository.save(existingUser);
        }
        if (!userName.isEmpty() || !password.isEmpty()){
            final APIUserDetail apiUserDetail = apiUserDetailRepository.findByUser_IdAndDeletedFalse(id);
            if (!userName.isEmpty()) apiUserDetail.setUsername(userName);
            if (!password.isEmpty()) apiUserDetail.setPasswordHash(password);
            apiUserDetailRepository.save(apiUserDetail);
        }
        return existingUser;
    }

    @Override
    public User get(String id) {
        notNull(id, "user request id can not be null");
        final User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new EntityNotFoundException();
        return user;
    }

    @Override
    public List<User> getAllByAdmin() {
        final List<User> users = userRepository.findAll();
        notEmpty(users, "users can not be empty");
        return users;
    }

    @Override
    public List<User> getAll(String userDetailId) {
        final List<User> users = new ArrayList<>();
        final Role adminRole = roleService.getByRoleType(RoleType.ROLE_ADMIN);
        final List<APIUserDetail> userDetails = apiUserDetailRepository.findAll();
        for (APIUserDetail userDetail: userDetails) {
            if (userDetail.getAuthorities().contains(adminRole) || userDetail.getId().equals(userDetailId)){
                continue;
            }
            users.add(userDetail.getUser());
        }
        notEmpty(users, "users can not be empty");
        return users;
    }

    private void validate(String name, String userName, String password, String roleName){
        notEmpty(name, "name can not be empty");
        notEmpty(userName, "userName can not be empty");
        notEmpty(password, "password can not be empty");
        notEmpty(roleName, "role name can not be empty");
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return apiUserDetailRepository.findByUsernameAndDeletedFalse(userName);
    }
}
