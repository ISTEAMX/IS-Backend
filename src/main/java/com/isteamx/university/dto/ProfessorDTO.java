package com.isteamx.university.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfessorDTO(
        Long id,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Department is required")
        String department
) {
}
