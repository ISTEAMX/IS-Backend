package com.isteamx.university.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isteamx.university.dto.ChangePasswordDTO;
import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.entity.User;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    private String adminToken;

    @BeforeEach
    void setUp() {
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole("ADMIN");
        admin.setPasswordChanged(true);
        userRepository.save(admin);

        adminToken = jwtUtil.generateToken("admin@test.com");
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        LoginDTO loginDTO = new LoginDTO("admin@test.com", "password123");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.userData.firstName").value("Admin"));
    }

    @Test
    void shouldRejectLoginWithWrongPassword() throws Exception {
        LoginDTO loginDTO = new LoginDTO("admin@test.com", "wrongpassword");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectLoginWithNonExistentEmail() throws Exception {
        LoginDTO loginDTO = new LoginDTO("nobody@test.com", "password123");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRegisterNewUserWithAdminAuth() throws Exception {
        ProfessorDTO professorDTO = new ProfessorDTO(null, "John", "Doe", "CS");
        UserDTO userDTO = new UserDTO(null, "John", "Doe", "john@test.com", "password123", "PROFESSOR", professorDTO);

        mockMvc.perform(post("/api/user/register")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void shouldRejectRegisterWithoutAuth() throws Exception {
        ProfessorDTO professorDTO = new ProfessorDTO(null, "John", "Doe", "CS");
        UserDTO userDTO = new UserDTO(null, "John", "Doe", "john@test.com", "password123", "PROFESSOR", professorDTO);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldChangePassword() throws Exception {
        ChangePasswordDTO dto = new ChangePasswordDTO("password123", "newPassword123");

        mockMvc.perform(put("/api/user/change-password")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed successfully"));

        // Verify login with new password works
        LoginDTO loginDTO = new LoginDTO("admin@test.com", "newPassword123");
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    @Test
    void shouldRejectChangePasswordWithWrongCurrent() throws Exception {
        ChangePasswordDTO dto = new ChangePasswordDTO("wrongpassword", "newPassword123");

        mockMvc.perform(put("/api/user/change-password")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldLoginWithoutAuth() throws Exception {
        // Login endpoint should be publicly accessible
        LoginDTO loginDTO = new LoginDTO("admin@test.com", "password123");

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }
}
