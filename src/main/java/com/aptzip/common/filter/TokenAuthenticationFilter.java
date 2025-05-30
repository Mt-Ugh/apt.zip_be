package com.aptzip.common.filter;

import com.aptzip.common.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // 요청 헤더의 Authorization 키의 값 조회
            String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
            // 가져온 값에서 접두사 제거
            String token = getAccessToken(authorizationHeader);
            // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
            if (tokenProvider.validToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String userUuid = tokenProvider.getUserId(token);
                request.setAttribute("userUuid", userUuid);
            }

            filterChain.doFilter(request, response);
        } catch (AuthenticationException | ServletException | IOException authEx) {
            // 인증·서블릿 오류는 그대로 다시 던져서 EntryPoint로 보냄
            throw authEx;

        } catch (Exception ex) {
            // 그 외(비즈니스, DB 제약 위반 등)는 Spring MVC (@ControllerAdvice)로
            throw ex;
        }
    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
