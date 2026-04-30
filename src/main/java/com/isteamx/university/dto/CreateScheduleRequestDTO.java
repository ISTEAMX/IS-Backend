package com.isteamx.university.dto;

import com.isteamx.university.enums.Frequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateScheduleRequestDTO(
        Long id,

        @NotBlank(message = "Schedule day is required")
        String scheduleDay,

        @NotBlank(message = "Starting hour is required")
        String startingHour,

        @NotBlank(message = "Ending hour is required")
        String endingHour,

        @NotNull(message = "Frequency is required")
        Frequency frequency,

        @Positive(message = "Professor ID must be a positive number")
        long professorId,

        @Positive(message = "Subject ID must be a positive number")
        long subjectId,

        @NotEmpty(message = "At least one group is required")
        List<Long> groupIds,

        @Positive(message = "Room ID must be a positive number")
        long roomId
) {
}
