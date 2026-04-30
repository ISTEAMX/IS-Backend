package com.isteamx.university.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record GroupDTO(
        Long id,

        @NotBlank(message = "Identifier is required")
        String identifier,

        @NotBlank(message = "Specialization is required")
        String specialization,

        @Positive(message = "Year must be a positive number")
        int year,

        @Positive(message = "Semester must be a positive number")
        int semester
) {
}
