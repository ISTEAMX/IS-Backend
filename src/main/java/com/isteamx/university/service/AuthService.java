package com.isteamx.university.service;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.RegisterDTO;
import com.isteamx.university.dto.UserDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);
    UserDTO register(RegisterDTO registerDTO);

}
