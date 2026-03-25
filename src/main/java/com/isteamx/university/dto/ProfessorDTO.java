package com.isteamx.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String department;
}
