package com.task_management_system.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.facade.authentication.AuthenticationFacade;
import com.task_management_system.facade.authentication.exception.AuthException;
import com.task_management_system.facade.authentication.model.AuthenticationRequest;
import com.task_management_system.facade.authentication.model.AuthenticationResponse;
import com.task_management_system.mapper.BeanMapper;
import com.task_management_system.security.dto.AuthRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private AuthenticationFacade authenticationFacade;
    private BeanMapper beanMapper;

    @Autowired
    public JwtAuthenticationFilter(final AuthenticationFacade authenticationFacade, final BeanMapper beanMapper) {
        this.authenticationFacade = authenticationFacade;
        this.beanMapper = beanMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            final AuthRequestDto credentials = new ObjectMapper().readValue(req.getInputStream(), AuthRequestDto.class);
            final AuthenticationRequest authenticationRequest = beanMapper.map(credentials, AuthenticationRequest.class);
            final AuthenticationResponse authenticationResponse = authenticationFacade.authenticateByCredentials(authenticationRequest);
            final APIUserDetail userDetail = authenticationResponse.getApiUserDetail();
            return new UsernamePasswordAuthenticationToken(userDetail, authenticationResponse.getToken(), userDetail.getAuthorities());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthException e) {
            res.setStatus(e.getHttpStatusCode());
            return null;
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = (String)auth.getCredentials();
        res.addHeader("Authorization", "Bearer " + token);
        handle(req, res, auth);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        final APIUserDetail apiUserDetail = (APIUserDetail) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if (isUser) {
            return "/user";
        } else if (isAdmin) {
            return "/admin";
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


}
