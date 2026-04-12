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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "Endpoints for user registration and login")
public class UserController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody UserDTO userDTO) {
         authService.register(userDTO);
         return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
    }

    @Operation(summary = "Authenticate user", description = "Returns a JWT token if credentials are valid.")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @PostMapping("/login")
    public ResponseLoginDTO loginUser(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
