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

        UserData userData = new UserData();
        userData.setFirstName("test");
        userData.setLastName("test");

        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
        responseLoginDTO.setUserData(userData);
        responseLoginDTO.setToken(token);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@test.com");
        loginDTO.setPassword("password");


        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


        when(passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())).thenReturn(true);


        when(jwtUtil.generateToken(loginDTO.getEmail())).thenReturn(token);

        ResponseLoginDTO response = authServiceImpl.login(loginDTO);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("Token");
        assertThat(response.getUserData().getFirstName()).isEqualTo("test");
        assertThat(response.getUserData().getLastName()).isEqualTo("test");
    }

    @Test
    public void shouldRegister() {

    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName("test");
    userDTO.setLastName("test");
    userDTO.setEmail("test@test.com");
    userDTO.setPassword("password");
    userDTO.setRole("PROFESSOR");
    ProfessorDTO professor = new ProfessorDTO();
    professor.setDepartment("department");
    userDTO.setProfessor(professor);


    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setEmail("test@test.com");
    savedUser.setPassword("encodedPassword");

    UserDTO expectedResponse = new UserDTO();
    expectedResponse.setEmail("test@test.com");


    when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    when(userDTOMapper.toDTO(savedUser)).thenReturn(expectedResponse);

    UserDTO response = authServiceImpl.register(userDTO);

    assertThat(response).isNotNull();
    assertThat(response.getEmail()).isEqualTo("test@test.com");
    }
}
