package com.isteamx.university.dto;


public record ScheduleDTO(long id,String scheduleDay,String startingHour,String endingHour,ProfessorDTO professorDTO,RoomDTO roomDTO,GroupDTO groupDTO,SubjectDTO subjectDTO) {
}
