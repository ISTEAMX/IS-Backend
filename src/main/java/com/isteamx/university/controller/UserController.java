package com.isteamx.university.controller;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ResponseLoginDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody UserDTO userDTO) {
         authService.register(userDTO);
         return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseLoginDTO loginUser(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
