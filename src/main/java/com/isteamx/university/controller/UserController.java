package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.ChangePasswordDTO;
import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ResponseLoginDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "Endpoints for user registration and login")
public class UserController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponseWrapper<Void>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success("User registered successfully"));
    }

    @Operation(summary = "Authenticate user", description = "Returns a JWT token if credentials are valid.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseWrapper<ResponseLoginDTO>> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(ApiResponseWrapper.success(authService.login(loginDTO), "Login successful"));
    }

    @Operation(summary = "Change password", description = "Allows an authenticated user to change their password.")
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponseWrapper<Void>> changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO,
            Authentication authentication) {
        authService.changePassword(authentication.getName(), changePasswordDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Password changed successfully"));
    }

}
