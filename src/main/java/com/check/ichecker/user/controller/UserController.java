package com.check.ichecker.user.controller;


import com.check.ichecker.user.domain.Users;
import com.check.ichecker.user.dto.TokenResponse;
import com.check.ichecker.user.dto.UserSignInRequest;
import com.check.ichecker.user.dto.UserSignUpRequest;
import com.check.ichecker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        return userService.findByUserId(userSignUpRequest.getUserId()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userSignUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserSignInRequest userSignUpRequest) throws Exception {

        return ResponseEntity.ok().body(userService.signIn(userSignUpRequest));
    }

    @GetMapping("/info")
    public ResponseEntity<List<Users>> findUser() {
        return ResponseEntity.ok().body(userService.findUsers());
    }
}