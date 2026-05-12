package com.isteamx.university.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isteamx.university.dto.*;
import com.isteamx.university.entity.*;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.repository.*;
import com.isteamx.university.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleIntegrationTest extends BaseIntegrationTest {

    @Autowired private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    private String adminToken;
    private Long professorId;
    private Long roomId;
    private Long subjectId;
    private Long groupId;

    @BeforeEach
    void setUp() {
        // Create admin
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole("ADMIN");
        admin.setPasswordChanged(true);
        userRepository.save(admin);
        adminToken = jwtUtil.generateToken("admin@test.com");

        // Create professor with user
        User profUser = new User();
        profUser.setFirstName("John");
        profUser.setLastName("Doe");
        profUser.setEmail("prof@test.com");
        profUser.setPassword(passwordEncoder.encode("password123"));
        profUser.setRole("PROFESSOR");
        profUser.setPasswordChanged(true);

        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setDepartment("CS");
        professor.setUser(profUser);
        profUser.setProfessor(professor);

        userRepository.save(profUser);
        professorId = professor.getId();

        // Create room
        Room room = new Room();
        room.setName("T204");
        room.setCapacity(60);
        room.setType("Lab");
        room.setLocation("T");
        roomRepository.save(room);
        roomId = room.getId();

        // Create subject
        Subject subject = new Subject();
        subject.setName("Algorithms");
        subject.setActivityType("Lab");
        subjectRepository.save(subject);
        subjectId = subject.getId();

        // Create group
        Group group = new Group();
        group.setIdentifier("TI-3A");
        group.setSpecialization("TI");
        group.setYear(3);
        group.setSemester(1);
        groupRepository.save(group);
        groupId = group.getId();
    }

    @Test
    void shouldAddSchedule() throws Exception {
        CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO(
                null, "Monday", "08:00", "10:00", Frequency.SAPTAMANAL,
                professorId, subjectId, List.of(groupId), roomId);

        mockMvc.perform(post("/api/schedule/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Schedule added successfully"))
                .andExpect(jsonPath("$.data.scheduleDay").value("Monday"))
                .andExpect(jsonPath("$.data.startingHour").value("08:00"))
                .andExpect(jsonPath("$.data.professorDTO.firstName").value("John"))
                .andExpect(jsonPath("$.data.roomDTO.name").value("T204"))
                .andExpect(jsonPath("$.data.groups", hasSize(1)));
    }

    @Test
    void shouldGetSchedulesByFilter() throws Exception {
        // Add a schedule
        CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO(
                null, "Monday", "08:00", "10:00", Frequency.SAPTAMANAL,
                professorId, subjectId, List.of(groupId), roomId);

        mockMvc.perform(post("/api/schedule/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Filter by professor
        mockMvc.perform(get("/api/schedule/user/filter")
                        .param("professorId", professorId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.content[0].scheduleDay").value("Monday"));

        // Filter by day that has no schedules
        mockMvc.perform(get("/api/schedule/user/filter")
                        .param("scheduleDay", "Friday"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(0)));
    }

    @Test
    void shouldRejectConflictingSchedule() throws Exception {
        // Add first schedule
        CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO(
                null, "Monday", "08:00", "10:00", Frequency.SAPTAMANAL,
                professorId, subjectId, List.of(groupId), roomId);

        String response = mockMvc.perform(post("/api/schedule/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Approve the first schedule so it becomes an active conflict
        Long scheduleId = objectMapper.readTree(response).get("data").get("id").asLong();
        mockMvc.perform(patch("/api/schedule/approve/" + scheduleId)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        // Try same slot — should conflict on group
        mockMvc.perform(post("/api/schedule/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldDeleteSchedule() throws Exception {
        CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO(
                null, "Monday", "08:00", "10:00", Frequency.SAPTAMANAL,
                professorId, subjectId, List.of(groupId), roomId);

        String response = mockMvc.perform(post("/api/schedule/add")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("data").get("id").asLong();

        mockMvc.perform(delete("/api/schedule/delete/" + id)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Schedule deleted successfully"));

        mockMvc.perform(get("/api/schedule/user/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllSchedulesPaginated() throws Exception {
        mockMvc.perform(get("/api/schedule/user")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.totalElements").isNumber());
    }
}


