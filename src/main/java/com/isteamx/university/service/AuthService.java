package com.isteamx.university.service;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.RegisterDTO;
import com.isteamx.university.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String login(LoginDTO loginDTO);
    UserDTO register(UserDTO userDTO);
    UserDTO register(RegisterDTO registerDTO);

}
