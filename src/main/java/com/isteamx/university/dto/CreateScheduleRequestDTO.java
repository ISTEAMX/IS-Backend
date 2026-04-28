package com.isteamx.university.dto;

import com.isteamx.university.enums.Frequency;

import java.util.List;

public record CreateScheduleRequestDTO(Long id, String scheduleDay, String startingHour, String endingHour, Frequency frequency, long professorId, long subjectId, List<Long> groupIds, long roomId) {
}
