package com.isteamx.university.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomDTO(
        Long id,

        @NotBlank(message = "Room name is required")
        String name,

        @Positive(message = "Capacity must be a positive number")
        int capacity,

        @NotBlank(message = "Room type is required")
        String type,

        @NotBlank(message = "Location is required")
        String location
) {
}
