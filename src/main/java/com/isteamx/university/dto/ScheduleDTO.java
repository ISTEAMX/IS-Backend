package com.isteamx.university.dto;


import com.isteamx.university.enums.Frequency;
import com.isteamx.university.enums.Pending;

import java.util.List;

public record ScheduleDTO(long id, String scheduleDay, String startingHour, String endingHour, Frequency frequency, Pending pending, ProfessorDTO professorDTO, RoomDTO roomDTO, List<GroupDTO> groups, SubjectDTO subjectDTO) {
}
