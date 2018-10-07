package com.task_management_system.service.api_auth_access_token;

import com.task_management_system.entity.ApiAuthAccessToken;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenCreationRequest;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenRequest;

import java.util.Optional;

public interface ApiAuthAccessTokenService {

    Optional<ApiAuthAccessToken> findByToken(String token);

    Optional<ApiAuthAccessToken> findByUserDetailId(String userDetailId);

    ApiAuthAccessToken createApiAccessToken(ApiAuthAccessTokenCreationRequest request);

    ApiAuthAccessToken refreshApiAccessToken(ApiAuthAccessTokenRequest request);

    void inactivateApiAccessToken(ApiAuthAccessTokenRequest request);

    void deleteApiAccessToken(ApiAuthAccessTokenRequest request);
}
