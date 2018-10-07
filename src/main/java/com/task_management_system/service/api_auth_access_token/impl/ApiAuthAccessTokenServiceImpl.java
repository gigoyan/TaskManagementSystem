package com.task_management_system.service.api_auth_access_token.impl;

import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.entity.ApiAuthAccessToken;
import com.task_management_system.misc.TokenType;
import com.task_management_system.repository.ApiAuthAccessTokenRepository;
import com.task_management_system.service.api_auth_access_token.ApiAuthAccessTokenConverter;
import com.task_management_system.service.api_auth_access_token.ApiAuthAccessTokenService;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenCreationRequest;
import com.task_management_system.service.api_auth_access_token.model.ApiAuthAccessTokenRequest;
import com.task_management_system.service.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

@Service
@PropertySource("classpath:application-security.properties")
public class ApiAuthAccessTokenServiceImpl implements ApiAuthAccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuthAccessTokenServiceImpl.class);

    @Value("${security.jwt.expiration.seconds:3600}")
    private int AUTH_ACCESS_TOKEN_EXPIRATION_SECONDS;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApiAuthAccessTokenRepository tokenRepository;

    @Override
    public Optional<ApiAuthAccessToken> findByToken(final String token) {
        hasText(token, "token can not be null");
        return Optional.ofNullable(tokenRepository.findByToken(token));
    }

    @Override
    public Optional<ApiAuthAccessToken> findByUserDetailId(final String userDetailId) {
        hasText(userDetailId, "userDetailId can not be null");
        return Optional.ofNullable(tokenRepository.findByUser(userDetailId));
    }

    @Override
    public ApiAuthAccessToken createApiAccessToken(final ApiAuthAccessTokenCreationRequest request) {
        notNull(request, "request can not be null");
        final APIUserDetail userDetail = request.getUserDetail();
        final TokenType tokenType = request.getTokenType();
        notNull(userDetail, "request.userDetail can not be null");
        notNull(tokenType, "request.tokenType can not be null");

        final String userId = userDetail.getUser().getId();
        logger.debug("Creating apiAuthAccessToken for userRequests:'{}'...", userId);
        final Date expires = createExpirationDate(new Date().getTime());
        request.setExpires(expires);
        final String token = tokenService.create(request);

        final ApiAuthAccessToken apiAuthAccessToken = tokenRepository.save(ApiAuthAccessTokenConverter.convert(request, token));
        logger.trace("ApiAuthAccessToken:'{}' is created for userRequests:'{}'.", apiAuthAccessToken.getToken(), userId);
        return apiAuthAccessToken;
    }

    @Override
    public ApiAuthAccessToken refreshApiAccessToken(final ApiAuthAccessTokenRequest request) {
        notNull(request, "request can not be null");
        final ApiAuthAccessToken token  = request.getToken();
        notNull(token, "request.token can not be null");

        logger.debug("Refreshing token for userRequests:'{}'...", token.getApiUserDetail().getUser().getId());
        token.setExpires(createExpirationDate(new Date().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        token.setActive(true);
        logger.trace("ApiAuthAccessToken:'{}' is refreshed for userRequests:'{}'.", token.getToken(), token.getApiUserDetail().getUser().getId());
        return tokenRepository.save(token);
    }

    @Override
    public void inactivateApiAccessToken(final ApiAuthAccessTokenRequest request) {
        notNull(request, "request can not be null");
        final ApiAuthAccessToken apiAuthAccessToken  = request.getToken();
        notNull(apiAuthAccessToken, "request.apiAuthAccessToken can not be null");

        apiAuthAccessToken.setActive(false);
        tokenRepository.save(apiAuthAccessToken);
    }

    @Override
    public void deleteApiAccessToken(ApiAuthAccessTokenRequest request) {
        notNull(request, "request can not be null");
        final ApiAuthAccessToken apiAuthAccessToken  = request.getToken();
        notNull(apiAuthAccessToken, "request.apiAuthAccessToken can not be null");

        apiAuthAccessToken.setActive(false);
        apiAuthAccessToken.setDeleted(true);
        tokenRepository.save(apiAuthAccessToken);
    }

    private Date createExpirationDate(final long time){
        return new Date(1000L * AUTH_ACCESS_TOKEN_EXPIRATION_SECONDS + time);
    }
}
