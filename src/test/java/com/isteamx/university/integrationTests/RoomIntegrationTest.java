package com.isteamx.university.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isteamx.university.dto.RoomDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomIntegrationTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    private String adminToken;
    private String userToken;

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

        User regularUser = new User();
        regularUser.setFirstName("Regular");
        regularUser.setLastName("User");
        regularUser.setEmail("user@test.com");
        regularUser.setPassword(passwordEncoder.encode("password123"));
        regularUser.setRole("USER");
        regularUser.setPasswordChanged(true);
        userRepository.save(regularUser);

        adminToken = jwtUtil.generateToken("admin@test.com");
        userToken = jwtUtil.generateToken("user@test.com");
    }

    @Test
    void shouldCreateRoomWithAdminAuth() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Room created successfully"))
                .andExpect(jsonPath("$.data.name").value("T204"))
                .andExpect(jsonPath("$.data.capacity").value(60));
    }

    @Test
    void shouldRejectCreateRoomWithNonAdminAuth() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetRoomsWithoutAuth() throws Exception {
        // Public endpoints should work without auth
        mockMvc.perform(get("/api/room/user/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    void shouldGetRoomById() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        String response = mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(get("/api/room/user/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("T204"))
                .andExpect(jsonPath("$.data.capacity").value(60));
    }

    @Test
    void shouldReturnPaginatedRooms() throws Exception {
        for (int i = 1; i <= 5; i++) {
            RoomDTO r = new RoomDTO(null, "Room-" + i, 30 + i, "Lab", "Building-" + i);
            mockMvc.perform(post("/api/room/create")
                            .header("Authorization", "Bearer " + adminToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(r)))
                    .andExpect(status().isCreated());
        }

        mockMvc.perform(get("/api/room/user/rooms")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(3)))
                .andExpect(jsonPath("$.data.totalElements").value(5))
                .andExpect(jsonPath("$.data.totalPages").value(2));
    }

    @Test
    void shouldRejectDuplicateRoom() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldUpdateRoom() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        String response = mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        RoomDTO updateDTO = new RoomDTO(id, "T205", 80, "Seminar", "T2");
        mockMvc.perform(put("/api/room/update")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Room updated successfully"));

        mockMvc.perform(get("/api/room/user/" + id))
                .andExpect(jsonPath("$.data.name").value("T205"))
                .andExpect(jsonPath("$.data.capacity").value(80));
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        RoomDTO roomDTO = new RoomDTO(null, "T204", 60, "Lab", "T");

        String response = mockMvc.perform(post("/api/room/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(delete("/api/room/delete/" + id)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Room deleted successfully"));

        mockMvc.perform(get("/api/room/user/" + id))
                .andExpect(status().isNotFound());
    }
}
