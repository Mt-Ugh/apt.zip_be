package com.aptzip.common.config;

import com.aptzip.auth.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.aptzip.auth.oauth.OAuth2SuccessHandler;
import com.aptzip.auth.oauth.OAuth2UserCustomService;
import com.aptzip.auth.repository.RedisRefreshTokenRepository;
import com.aptzip.common.config.jwt.JwtProperties;
import com.aptzip.common.config.jwt.TokenProvider;
import com.aptzip.common.filter.TokenAuthenticationFilter;
import com.aptzip.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final UserRepository userRepository;
    private final OAuth2UserCustomService oAuth2UserCustomService;

    @Value("${client.redirect-uri}")
    private String redirectUri;

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(
                tokenProvider,
                jwtProperties,
                redisRefreshTokenRepository,
                userRepository,
                redirectUri
        );
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**", "/oauth2/**",
                                "/user/signup",
                                "/review/list",
                                "/review/detail",
                                "/qna/list",
                                "/qna/detail",
                                "/interestArea/regist",
                                "/interestArea/fame",
                                "/dealMap/**",
                                "/amenitiesMap/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .authorizationRequestRepository(authorizationRequestRepository())
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserCustomService)
                        )
                        .successHandler(oAuth2SuccessHandler())
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (authException instanceof AuthenticationException) {
                                // 인증 실패만 401
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다.");
                            } else {
                                // 그 외는 ControllerAdvice로
                                throw authException;
                            }
                        })
                )
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
