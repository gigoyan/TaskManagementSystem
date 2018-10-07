package com.task_management_system.facade.authentication;

import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.misc.TokenType;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenCreationRequest;

import java.util.Date;

public class AuthModelConverter {
    public static ApiAuthAccessTokenCreationRequest convert(final APIUserDetail userDetail, final boolean isRememberMe) {
        final ApiAuthAccessTokenCreationRequest apiAuthAccessTokenCreationRequest = new ApiAuthAccessTokenCreationRequest();
        apiAuthAccessTokenCreationRequest.setUserDetail(userDetail);
        apiAuthAccessTokenCreationRequest.setTokenType(isRememberMe ? TokenType.LOGIN_REMEMBER_ME : TokenType.LOGIN);
        apiAuthAccessTokenCreationRequest.setActive(true);
        apiAuthAccessTokenCreationRequest.setExpires(new Date());
        return apiAuthAccessTokenCreationRequest;
    }
}
