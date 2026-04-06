package com.isteamx.university.service;

import com.isteamx.university.dto.LoginDTO;
import com.isteamx.university.dto.ResponseLoginDTO;
import com.isteamx.university.dto.UserDTO;


public interface AuthService {

    ResponseLoginDTO login(LoginDTO loginDTO);
    UserDTO register(UserDTO userDTO);

}
