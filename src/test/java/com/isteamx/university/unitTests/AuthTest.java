package com.isteamx.university.unitTests;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.UserDTO;
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
    public void testLogin() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@test.com");
        loginDTO.setPassword("password");


        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


        when(passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())).thenReturn(true);

        String token = "Token";

        when(jwtUtil.generateToken(loginDTO.getEmail())).thenReturn(token);

        String response = authServiceImpl.login(loginDTO);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(token);
    }

    @Test
    public void testRegister() {

    UserDTO userDTO = new UserDTO();
    userDTO.setEmail("test@test.com");
    userDTO.setPassword("password");
    userDTO.setRole("USER");
    ProfessorDTO professor = new ProfessorDTO();
    userDTO.setProfessor(professor);

    User newUser = new User();
    newUser.setEmail("test@test.com");
    newUser.setPassword("password");

    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setEmail("test@test.com");
    savedUser.setPassword("encodedPassword");

    UserDTO newUserDTO = new UserDTO();
    newUserDTO.setEmail("test@test.com");
    newUserDTO.setPassword("encodedPassword");

    when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

    when(userDTOMapper.toEntity(userDTO)).thenReturn(newUser);

    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

    when(userRepository.save(newUser)).thenReturn(savedUser);

    when(userDTOMapper.toDTO(savedUser)).thenReturn(newUserDTO);

    UserDTO response = authServiceImpl.register(userDTO);

    assertThat(response).isNotNull();
    assertThat(response.getEmail()).isEqualTo(savedUser.getEmail());
    }
}
