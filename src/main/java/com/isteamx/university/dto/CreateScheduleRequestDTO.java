package com.isteamx.university.dto;

public record CreateScheduleRequestDTO(String scheduleDay, String startingHour, String endingHour,long professorId,long subjectId,long groupId,long roomId) {
}
