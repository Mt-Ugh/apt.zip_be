package com.aptzip.user.controller;

import com.aptzip.auth.service.AuthService;
import com.aptzip.common.util.CookieUtil;
import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.dto.request.UpdateUserRequest;
import com.aptzip.user.dto.response.UserDetailResponse;
import com.aptzip.user.entity.User;
import com.aptzip.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody AddUserRequest addUserRequest) {
        userService.save(addUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/detail")
    public ResponseEntity<UserDetailResponse> userDetail(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.findByUuid(user.getUserUuid()));
    }

    @PutMapping("/update/profile")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal User user, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(user.getUserUuid(), updateUserRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/profile/image")
    public ResponseEntity<Void> updateProfileImage(@AuthenticationPrincipal User user, @RequestParam("profileImage") MultipartFile profileImage) {
        userService.updateProfileImage(user.getUserUuid(), profileImage);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> withdrawUser(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        authService.logout(user.getUserUuid());
        userService.withdrawUser(user.getUserUuid());
        return ResponseEntity.ok().build();
    }
}
