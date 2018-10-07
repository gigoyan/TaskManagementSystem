package com.task_management_system.facade.authentication.impl;


import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.entity.ApiAuthAccessToken;
import com.task_management_system.facade.authentication.AuthModelConverter;
import com.task_management_system.facade.authentication.AuthenticationFacade;
import com.task_management_system.facade.authentication.exception.AuthException;
import com.task_management_system.facade.authentication.model.APIAuthenticationResponse;
import com.task_management_system.facade.authentication.model.AuthenticationRequest;
import com.task_management_system.facade.authentication.model.AuthenticationResponse;
import com.task_management_system.facade.strategy.AuthValidationStrategy;
import com.task_management_system.service.api_auth_access_token.ApiAuthAccessTokenService;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenRequest;
import com.task_management_system.service.user_detail.ApiUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.hasText;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFacadeImpl.class);

    @Autowired
    private ApiUserDetailService apiUserDetailService;

    @Autowired
    private ApiAuthAccessTokenService apiAuthAccessTokenService;

    @Autowired
    private AuthValidationStrategy authValidationStrategy;

    @Value("#{'${authenticationService.masterApiUserDetail.passwordHash}' ?: null}")
    private String masterApiUserDetailPasswordHash;

    @Override
    public AuthenticationResponse authenticateByCredentials(final AuthenticationRequest request) throws AuthException {
        authValidationStrategy.validate(request);
        final APIUserDetail userDetail = apiUserDetailService.loadUserByUsername(request.getUsername());
        authValidationStrategy.validate(userDetail);
        final ApiAuthAccessToken apiAuthAccessToken = apiAuthAccessTokenService.createApiAccessToken(AuthModelConverter.convert(userDetail, request.isRememberMe()));
        return new AuthenticationResponse(userDetail, apiAuthAccessToken.getToken());
    }

    @Override
    public AuthenticationResponse authenticateByApiAccessToken(final String token) throws AuthException {
        ApiAuthAccessToken existingToken = apiAuthAccessTokenService.findByToken(token).orElse(null);
        authValidationStrategy.validateForRefreshing(existingToken);
        if (authValidationStrategy.isExpiredLoginToken(existingToken)) {
            apiAuthAccessTokenService.inactivateApiAccessToken(new ApiAuthAccessTokenRequest(existingToken));
            throw new AuthException(String.format("Api userRequests access token is expired:'%s'.", existingToken.getApiUserDetail().getUser().getId()));
        }
        if (authValidationStrategy.isExpiringRefreshToken(existingToken)) {
            existingToken = apiAuthAccessTokenService.refreshApiAccessToken(new ApiAuthAccessTokenRequest(existingToken));
        }
        return new AuthenticationResponse(existingToken.getApiUserDetail(), existingToken.getToken());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthException {
        return new APIAuthenticationResponse(authenticateByCredentials((AuthenticationRequest) authentication.getDetails()));
    }

    @Override
    public void logout(String token) {
        hasText(token, "token can not be null or empty.");
        ApiAuthAccessToken existingToken = apiAuthAccessTokenService.findByToken(token).orElse(null);
        if (existingToken != null) {
            apiAuthAccessTokenService.deleteApiAccessToken(new ApiAuthAccessTokenRequest(existingToken));
        }
    }
}
