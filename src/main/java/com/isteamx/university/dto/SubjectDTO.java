package com.isteamx.university.dto;

import jakarta.validation.constraints.NotBlank;

public record SubjectDTO(
        Long id,

        @NotBlank(message = "Subject name is required")
        String name,

        @NotBlank(message = "Activity type is required")
        String activityType
) {
}
