package com.isteamx.university.service.impl;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.RegisterDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.service.AuthService;
import com.isteamx.university.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;
    private final ProfessorDTOMapper professorDTOMapper;
    private final JwtUtil jwtUtil;


    @Override
    public String login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Failed login attempt for email: {}", email);
            throw new BadCredentialsException("Invalid email or password");
        }

        log.info("Successful login for user: {}", email);
        return jwtUtil.generateToken(email);
    }

    @Override
    public UserDTO register(RegisterDTO registerDTO) {

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Role is always assigned server-side — never from client input
        if (registerDTO.getProfessor() != null) {
            user.setRole("PROFESSOR");
            Professor professor = professorDTOMapper.toEntity(registerDTO.getProfessor());
            professor.setUser(user);
            user.setProfessor(professor);
        } else {
            user.setRole("USER");
        }

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getEmail());

        return userDTOMapper.toDTO(savedUser);
    }
}
