package com.isteamx.university.unitTests;

import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.UserDTOMapper;
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

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Test
    public void shouldLogin() {

        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        String token = "Token";


        UserData userData = new UserData(1L, 1L,"test", "test","AMDIN");
        LoginDTO loginDTO = new LoginDTO("test@test.com", "password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(true);

        when(jwtUtil.generateToken(loginDTO.email())).thenReturn(token);

        ResponseLoginDTO response = authServiceImpl.login(loginDTO);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo("Token");
        assertThat(response.userData().firstName()).isEqualTo("test");
        assertThat(response.userData().lastName()).isEqualTo("test");
    }

    @Test
    public void shouldRegister() {
        ProfessorDTO professor = new ProfessorDTO(1L,"test","test","department");

        UserDTO userDTO = new UserDTO(1L, "test", "test", "test@test.com", "password", "PROFESSOR", professor);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@test.com");
        savedUser.setPassword("encodedPassword");

        UserDTO expectedResponse = new UserDTO(1L, "test", "test", "test@test.com", null, "PROFESSOR", professor);

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        when(userDTOMapper.toDTO(savedUser)).thenReturn(expectedResponse);

        UserDTO response = authServiceImpl.register(userDTO);

        assertThat(response).isNotNull();
        // Atenție: email() în loc de getEmail()
        assertThat(response.email()).isEqualTo("test@test.com");
    }
}