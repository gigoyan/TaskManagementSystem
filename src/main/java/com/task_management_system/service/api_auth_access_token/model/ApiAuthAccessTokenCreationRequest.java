package com.task_management_system.service.api_auth_access_token.model;

import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.misc.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiAuthAccessTokenCreationRequest {
    private APIUserDetail userDetail;
    private TokenType tokenType;
    private boolean isActive;
    private Date expires;
}
