package com.task_management_system.service.token;

import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenCreationRequest;

public interface TokenService {

    String create(ApiAuthAccessTokenCreationRequest request);

    String refresh(final String token);

    String getUserDetailId(final String token);
}
