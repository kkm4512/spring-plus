package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.example.expert.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/users")
    public void changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        AuthUser authUser = userDetails.getAuthUser();
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    // 유저의 닉네임으로 조회하는 API
    @GetMapping("/users/search")
    public ResponseEntity<UserResponse> searchUser(
            @RequestParam String nickname
    ) {
        return ResponseEntity.ok(userService.searchUser(nickname));
    }
}
