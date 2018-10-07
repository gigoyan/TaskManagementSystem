package com.task_management_system.facade.authentication.model;

import com.task_management_system.entity.APIUserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse{
    private APIUserDetail apiUserDetail;
    private String token;
}
