package com.isteamx.university.unitTests;

import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.exception.UserUnauthorizedException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

        Professor mockProfessor = new Professor();
        mockProfessor.setId(1L);
        user.setProfessor(mockProfessor);


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

    @Test
    public void shouldThrowExceptionWhenLoginEmailNotFound() {
        LoginDTO loginDTO = new LoginDTO("notfound@test.com", "password");

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authServiceImpl.login(loginDTO));
    }

    @Test
    public void shouldThrowExceptionWhenLoginPasswordIncorrect() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        LoginDTO loginDTO = new LoginDTO("test@test.com", "wrongPassword");

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(false);

        assertThrows(UserUnauthorizedException.class, () -> authServiceImpl.login(loginDTO));
    }

    @Test
    public void shouldThrowExceptionWhenRegisterEmailAlreadyExists() {
        ProfessorDTO professor = new ProfessorDTO(1L, "test", "test", "department");
        UserDTO userDTO = new UserDTO(1L, "test", "test", "existing@test.com", "password", "PROFESSOR", professor);

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.of(new User()));

        assertThrows(AlreadyExistsException.class, () -> authServiceImpl.register(userDTO));
    }

    @Test
    public void shouldRegisterAdminWithoutProfessorEntity() {
        UserDTO userDTO = new UserDTO(1L, "Admin", "User", "admin@test.com", "password", "ADMIN", null);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("admin@test.com");
        savedUser.setRole("ADMIN");

        UserDTO expectedResponse = new UserDTO(1L, "Admin", "User", "admin@test.com", null, "ADMIN", null);

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userDTOMapper.toDTO(savedUser)).thenReturn(expectedResponse);

        UserDTO response = authServiceImpl.register(userDTO);

        assertThat(response).isNotNull();
        assertThat(response.role()).isEqualTo("ADMIN");
    }

    @Test
    public void shouldChangePassword() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedOldPassword");
        user.setPasswordChanged(false);

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("oldPassword", "newPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        authServiceImpl.changePassword(email, changePasswordDTO);

        verify(userRepository).save(user);
        assertThat(user.getPasswordChanged()).isTrue();
    }

    @Test
    public void shouldThrowExceptionWhenChangePasswordUserNotFound() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("old", "new");

        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authServiceImpl.changePassword("notfound@test.com", changePasswordDTO));
    }

    @Test
    public void shouldThrowExceptionWhenChangePasswordCurrentPasswordIncorrect() {
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("wrongPassword", "newPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(UserUnauthorizedException.class,
                () -> authServiceImpl.changePassword(email, changePasswordDTO));
    }

    @Test
    public void shouldLoginAndReturnNullProfessorIdWhenNoProfessor() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setEmail("admin@test.com");
        user.setPassword("encodedPassword");
        user.setRole("ADMIN");
        user.setProfessor(null);
        user.setPasswordChanged(true);

        LoginDTO loginDTO = new LoginDTO("admin@test.com", "password");

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(loginDTO.email())).thenReturn("Token");

        ResponseLoginDTO response = authServiceImpl.login(loginDTO);

        assertThat(response).isNotNull();
        assertThat(response.userData().professorId()).isNull();
    }
}

