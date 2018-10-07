package com.task_management_system.security.jwt;

import com.task_management_system.entity.APIUserDetail;
import com.task_management_system.facade.authentication.AuthenticationFacade;
import com.task_management_system.facade.authentication.exception.AuthException;
import com.task_management_system.facade.authentication.model.AuthenticationResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private AuthenticationFacade authenticationFacade;

    public JWTAuthorizationFilter(AuthenticationFacade authenticationManager) {
        super(authenticationManager);
        this.authenticationFacade = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                final AuthenticationResponse authentication = getAuthentication(header);
                final APIUserDetail userDetail = authentication.getApiUserDetail();
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetail, authentication.getToken(), userDetail.getAuthorities()));
                chain.doFilter(req, res);

            } catch (AuthException e) {
                SecurityContextHolder.getContext().setAuthentication(null);
                res.setStatus(e.getHttpStatusCode());
            } catch (IllegalArgumentException e){
                SecurityContextHolder.getContext().setAuthentication(null);
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private AuthenticationResponse getAuthentication(final String header) throws AuthException {
        final String token = header.replaceAll("Bearer ", "");
        return authenticationFacade.authenticateByApiAccessToken(token);
    }
}
