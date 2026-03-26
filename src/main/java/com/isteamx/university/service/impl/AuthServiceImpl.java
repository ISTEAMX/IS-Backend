package com.isteamx.university.service.impl;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.dtoMapper.UserDTOMapper;
import com.isteamx.university.entity.User;
import com.isteamx.university.repository.UserRepository;
import com.isteamx.university.service.AuthService;
import com.isteamx.university.util.JwtUtil;
import lombok.RequiredArgsConstructor;
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
    public String login(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Sorry this email is not registered"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(loginDTO.getEmail());
    }

    @Override
    public UserDTO register(UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = userDTOMapper.toEntity(userDTO);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);


        User savedUser =  userRepository.save(user);

        return userDTOMapper.toDTO(savedUser);
    }
}
