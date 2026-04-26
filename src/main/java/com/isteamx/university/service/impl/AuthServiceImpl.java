package com.isteamx.university.service.impl;

import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.exception.UserUnauthorizedException;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.service.AuthService;
import com.isteamx.university.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;
    private final JwtUtil jwtUtil;


    @Override
    public ResponseLoginDTO login(LoginDTO loginDTO) {



        String email = loginDTO.email();
        String password = loginDTO.password();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Sorry this email is not registered"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new UserUnauthorizedException("Incorrect password");
        }

        Long professorId = user.getProfessor() != null ? user.getProfessor().getId() : null;
        UserData userData = new UserData(user.getId(),professorId,user.getFirstName(),user.getLastName(),user.getRole(),user.getPasswordChanged());

         String token = jwtUtil.generateToken(loginDTO.email());

        return new ResponseLoginDTO(token,userData);

    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public UserDTO register( UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.email()).isPresent()){
            throw new AlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRole(userDTO.role());
        user.setPasswordChanged(false);

        Professor professor = new Professor();
        professor.setFirstName(userDTO.firstName());
        professor.setLastName(userDTO.lastName());
        if (userDTO.professor() != null && userDTO.professor().department() != null) {
            professor.setDepartment(userDTO.professor().department());
        } else {
            professor.setDepartment("Default Department");
        }
        professor.setUser(user);
        user.setProfessor(professor);

        User savedUser = userRepository.save(user);

        return userDTOMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordDTO.currentPassword(), user.getPassword())) {
            throw new UserUnauthorizedException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        user.setPasswordChanged(true);
        userRepository.save(user);
    }
}
