package com.isteamx.university.dto;


public record UserDTO(Long id, String firstName, String lastName, String email,String password, String role, ProfessorDTO professor) {}
