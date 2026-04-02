package com.isteamx.university.dto;

import com.isteamx.university.enums.Frequency;

public record CreateScheduleRequestDTO(Long id,String scheduleDay, String startingHour, String endingHour, Frequency frequency, long professorId, long subjectId, long groupId, long roomId) {
}
