package com.isteamx.university.service.impl;

import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.exception.UserUnauthorizedException;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.service.AuthService;
import com.isteamx.university.util.JwtUtil;
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



        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Sorry this email is not registered"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new UserUnauthorizedException("Incorrect password");
        }

        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setRole(user.getRole());

        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
        responseLoginDTO.setUserData(userData);
        responseLoginDTO.setToken(jwtUtil.generateToken(loginDTO.getEmail()));


        return responseLoginDTO;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public UserDTO register( UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        Professor professor = new Professor();
        professor.setFirstName(userDTO.getFirstName());
        professor.setLastName(userDTO.getLastName());
        professor.setDepartment(userDTO.getProfessor().getDepartment());
        professor.setUser(user);
        user.setProfessor(professor);

        User savedUser = userRepository.save(user);

        return userDTOMapper.toDTO(savedUser);
    }
}
