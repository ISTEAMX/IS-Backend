package com.isteamx.university.dto;


import com.isteamx.university.enums.Frequency;

import java.util.List;

public record ScheduleDTO(long id, String scheduleDay, String startingHour, String endingHour, Frequency frequency, ProfessorDTO professorDTO, RoomDTO roomDTO, List<GroupDTO> groups, SubjectDTO subjectDTO) {
}
