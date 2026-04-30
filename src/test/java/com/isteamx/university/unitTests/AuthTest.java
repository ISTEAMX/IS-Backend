package com.isteamx.university.unitTests;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.RegisterDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.service.impl.AuthServiceImpl;
import com.isteamx.university.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @Mock
    JwtUtil jwtUtil;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    UserDTOMapper userDTOMapper;

    @Mock
    ProfessorDTOMapper professorDTOMapper;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Test
    public void shouldLogin() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@test.com");
        loginDTO.setPassword("password");


        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);

        String token = "Token";

        when(jwtUtil.generateToken(loginDTO.getEmail())).thenReturn(token);

        String response = authServiceImpl.login(loginDTO);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(token);
    }

    @Test
    public void shouldRegister() {

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@test.com");
        registerDTO.setPassword("password123");
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setFirstName("John");
        professorDTO.setLastName("Doe");
        professorDTO.setDepartment("CS");
        registerDTO.setProfessor(professorDTO);

        Professor professor = new Professor();
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setDepartment("CS");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@test.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole("PROFESSOR");

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1L);
        expectedDTO.setEmail("test@test.com");
        expectedDTO.setRole("PROFESSOR");

        when(userRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.empty());
        when(professorDTOMapper.toEntity(professorDTO)).thenReturn(professor);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userDTOMapper.toDTO(savedUser)).thenReturn(expectedDTO);

        UserDTO response = authServiceImpl.register(registerDTO);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(response.getRole()).isEqualTo("PROFESSOR");
    }
}
