package com.isteamx.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    public Long id;
    public String email;
    public String password;
    public String role;
    public ProfessorDTO professor;
}
