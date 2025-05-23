package com.aptzip.user.controller;

import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.dto.request.UpdateProfileUrlRequest;
import com.aptzip.user.dto.request.UpdateUserRequest;
import com.aptzip.user.dto.response.UserDetailResponse;
import com.aptzip.user.entity.User;
import com.aptzip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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

    @PutMapping("/update/profileUrl")
    public ResponseEntity<Void> updateProfileUrl(@AuthenticationPrincipal User user, @RequestBody UpdateProfileUrlRequest updateProfileUrlRequest) {
        userService.updateProfileUrl(user.getUserUuid(), updateProfileUrlRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> withdrawUser(@AuthenticationPrincipal User user) {
        userService.withdrawUser(user.getUserUuid());
        return ResponseEntity.ok().build();
    }
}
