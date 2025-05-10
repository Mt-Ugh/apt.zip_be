package com.aptzip.auth.oauth;

import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    // 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> User.builder()
                        .email(email)
                        .name(attributes.getOrDefault("name", "소셜유저").toString())
                        .nickname("user_" + UUID.randomUUID().toString().substring(0, 8))
                        .password(UUID.randomUUID().toString()) // 비밀번호는 OAuth 로그인 사용자에겐 무의미하므로 난수
                        .phoneNumber("010-0000-0000") // 기본값
                        .profileUrl((String) attributes.getOrDefault("picture", ""))
                        .build());

        return userRepository.save(user);
    }
}
