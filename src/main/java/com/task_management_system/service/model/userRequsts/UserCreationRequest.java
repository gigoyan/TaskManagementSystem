package com.task_management_system.service.model.userRequsts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {

    private String name;

    private String userName;

    private String password;

    private String roleId;
}
