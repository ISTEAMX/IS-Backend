package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleDTOMapper {

    private final ProfessorDTOMapper professorMapper;
    private final RoomDTOMapper roomMapper;
    private final GroupDTOMapper groupMapper;
    private final SubjectDTOMapper subjectMapper;


    public ScheduleDTO toDTO(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getScheduleDay(),
                schedule.getStartingHour(),
                schedule.getEndingHour(),
                schedule.getFrequency(),
                professorMapper.toDTO(schedule.getProfessor()),
                roomMapper.toDTO(schedule.getRoom()),
                groupMapper.toDTO(schedule.getGroup()),
                subjectMapper.toDTO(schedule.getSubject())
        );
    }

    public Schedule toEntity(ScheduleDTO dto) {
        if (dto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        schedule.setId(dto.id());
        schedule.setScheduleDay(dto.scheduleDay());
        schedule.setStartingHour(dto.startingHour());
        schedule.setEndingHour(dto.endingHour());
        schedule.setFrequency(dto.frequency());
        schedule.setProfessor(professorMapper.toEntity(dto.professorDTO()));
        schedule.setRoom(roomMapper.toEntity(dto.roomDTO()));
        schedule.setGroup(groupMapper.toEntity(dto.groupDTO()));
        schedule.setSubject(subjectMapper.toEntity(dto.subjectDTO()));

        return schedule;
    }
}