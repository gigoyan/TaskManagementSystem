package com.task_management_system.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.util.Assert.*;

@Component
@PropertySource("classpath:application-security.properties")
public class JwtTokenComponent {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenComponent.class);

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${security.jwt.token.secret}")
    private String JWT_TOKEN_SECRET;

    @Value("${security.jwt.header:Authorization}")
    private String JWT_HEADER;

    @Value("${security.jwt.cookie:SO_AUTH_TOKEN}")
    private String JWT_COOKIE;

    private String authenticationHeaderValuePrefix = "Bearer ";

    public String createToken(final Map<String, Object> claimsMap) {
        notEmpty(claimsMap, "claims map can not be null or empty.");
        final String encodedKey = TextCodec.BASE64.encode(JWT_TOKEN_SECRET);
        return Jwts.builder()
                .setHeader(assembleHeader())
                .setClaims(claimsMap)
                .signWith(signatureAlgorithm, encodedKey)
                .compact();
    }

    public String getClaim(final String token, final String key) {
        hasText(token, "token can not be null or empty.");
        hasText(key, "key can not be null or empty.");
        final Claims claims = getClaims(token);
        return getClaimValueFromToken(claims, key);
    }

    public Optional<String> getCookie(final HttpServletRequest request, final String cookieKey) {
        notNull(request, "request can not be null.");
        hasText(cookieKey, "cookieKey can not be null or empty.");
        return request.getCookies() != null ? Stream.of(request.getCookies()).filter(c -> c.getName().equals(cookieKey))
                .map(Cookie::getValue).findFirst() : Optional.empty();
    }

    private Optional<String> getHeader(final HttpServletRequest request, final String headerKey) {
        notNull(request, "request can not be null.");
        hasText(headerKey, "headerKey can not be null or empty.");
        return Optional.ofNullable(request.getHeader(headerKey))
                .map(header -> header.startsWith(authenticationHeaderValuePrefix)
                        ? header.substring(authenticationHeaderValuePrefix.length())
                        : header);
    }

    public Optional<String> getToken(final HttpServletRequest request) {
        notNull(request, "request can not be null.");
        return Optional.ofNullable(
                getHeader(request, JWT_HEADER)
                        .orElseGet(() -> getCookie(request, JWT_COOKIE)
                                .orElse(null)));
    }

    public Claims getClaims(final String token) {
        hasText(token, "token can not be null or empty.");
        final String encodedKey = TextCodec.BASE64.encode(JWT_TOKEN_SECRET);
        return Jwts.parser().setSigningKey(encodedKey).setAllowedClockSkewSeconds(60000).parseClaimsJws(token).getBody();
    }

    public void setCookie(final String token, final HttpServletResponse response) {
        hasText(token, "token can not be null or empty.");
        notNull(response, "response can not be null.");
        final Cookie cookie = new Cookie(JWT_COOKIE, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private Map<String, Object> assembleHeader(){
        final Map<String, Object> headerParamsMap = new HashMap<>();
        headerParamsMap.put("alg", "HS256");
        headerParamsMap.put("typ", "JWT");
        return headerParamsMap;
    }

    private String getClaimValueFromToken(final Claims claims, final String key) {
        notNull(claims, "claims can not be null.");
        hasText(key, "key can not be null or empty.");
        return claims.get(key, String.class);
    }
}
