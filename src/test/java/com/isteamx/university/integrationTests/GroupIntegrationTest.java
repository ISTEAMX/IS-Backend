package com.isteamx.university.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isteamx.university.dto.GroupDTO;
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
public class GroupIntegrationTest extends BaseIntegrationTest {

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
    void shouldReturnEmptyPageWhenNoGroups() throws Exception {
        mockMvc.perform(get("/api/group/user/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Groups retrieved successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(0)))
                .andExpect(jsonPath("$.data.totalElements").value(0));
    }

    @Test
    void shouldCreateGroupWithAdminAuth() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Group created successfully"))
                .andExpect(jsonPath("$.data.identifier").value("TI-3A"))
                .andExpect(jsonPath("$.data.specialization").value("TI"))
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @Test
    void shouldRejectCreateGroupWithoutAuth() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        mockMvc.perform(post("/api/group/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectDuplicateGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        // Create first
        mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andExpect(status().isCreated());

        // Try duplicate
        mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Group already exists"));
    }

    @Test
    void shouldGetGroupById() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        String response = mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(get("/api/group/user/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identifier").value("TI-3A"))
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    void shouldReturn404WhenGroupNotFound() throws Exception {
        mockMvc.perform(get("/api/group/user/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Group not found"));
    }

    @Test
    void shouldReturnPaginatedGroups() throws Exception {
        // Create 3 groups
        for (int i = 1; i <= 3; i++) {
            GroupDTO g = new GroupDTO(null, "G-" + i, "TI", 3, 1);
            mockMvc.perform(post("/api/group/create")
                            .header("Authorization", "Bearer " + adminToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(g)))
                    .andExpect(status().isCreated());
        }

        // Request page 0, size 2
        mockMvc.perform(get("/api/group/user/groups")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(2)))
                .andExpect(jsonPath("$.data.totalElements").value(3))
                .andExpect(jsonPath("$.data.totalPages").value(2));

        // Request page 1, size 2
        mockMvc.perform(get("/api/group/user/groups")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)));
    }

    @Test
    void shouldUpdateGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        String response = mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        GroupDTO updateDTO = new GroupDTO(id, "TI-3B", "C", 2, 1);
        mockMvc.perform(put("/api/group/update")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Group updated successfully"));

        // Verify update
        mockMvc.perform(get("/api/group/user/" + id))
                .andExpect(jsonPath("$.data.identifier").value("TI-3B"))
                .andExpect(jsonPath("$.data.specialization").value("C"));
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO(null, "TI-3A", "TI", 3, 1);

        String response = mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupDTO)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(delete("/api/group/delete/" + id)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Group deleted successfully"));

        // Verify deleted
        mockMvc.perform(get("/api/group/user/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectInvalidGroupData() throws Exception {
        GroupDTO invalidDTO = new GroupDTO(null, "", "", 0, 0);

        mockMvc.perform(post("/api/group/create")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}
