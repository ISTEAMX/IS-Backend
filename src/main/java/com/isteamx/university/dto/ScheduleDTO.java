package com.isteamx.university.dto;


import com.isteamx.university.enums.Frequency;

public record ScheduleDTO(long id, String scheduleDay, String startingHour, String endingHour, Frequency frequency, ProfessorDTO professorDTO, RoomDTO roomDTO, GroupDTO groupDTO, SubjectDTO subjectDTO) {
}
