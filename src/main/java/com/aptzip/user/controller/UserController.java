package com.aptzip.user.controller;

import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.dto.request.UpdateProfileUrlRequest;
import com.aptzip.user.dto.request.UpdateUserRequest;
import com.aptzip.user.dto.response.UserDetailResponse;
import com.aptzip.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(AddUserRequest addUserRequest) {
        userService.save(addUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/detail/{userUuid}")
    public ResponseEntity<UserDetailResponse> userDetail(@PathVariable("userUuid") String userUuid) {
        return ResponseEntity.ok(userService.findByUuid(userUuid));
    }

    @PutMapping("/update/profile/{userUuid}")
    public ResponseEntity<Void> updateUser(@PathVariable("userUuid") String userUuid, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(userUuid, updateUserRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/profileUrl/{userUuid}")
    public ResponseEntity<Void> updateProfileUrl(@PathVariable("userUuid") String userUuid, @RequestBody UpdateProfileUrlRequest updateProfileUrlRequest) {
        userService.updateProfileUrl(userUuid, updateProfileUrlRequest);
        return ResponseEntity.ok().build();
    }
}
