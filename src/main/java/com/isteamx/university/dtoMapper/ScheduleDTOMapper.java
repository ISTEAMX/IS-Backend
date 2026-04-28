package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        List<GroupDTO> groupDTOs = schedule.getGroups() != null
                ? schedule.getGroups().stream().map(groupMapper::toDTO).collect(Collectors.toList())
                : Collections.emptyList();

        return new ScheduleDTO(
                schedule.getId(),
                schedule.getScheduleDay(),
                schedule.getStartingHour(),
                schedule.getEndingHour(),
                schedule.getFrequency(),
                schedule.getPending(),
                professorMapper.toDTO(schedule.getProfessor()),
                roomMapper.toDTO(schedule.getRoom()),
                groupDTOs,
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
        schedule.setPending(dto.pending());
        schedule.setProfessor(professorMapper.toEntity(dto.professorDTO()));
        schedule.setRoom(roomMapper.toEntity(dto.roomDTO()));
        if (dto.groups() != null) {
            schedule.setGroups(dto.groups().stream().map(groupMapper::toEntity).collect(Collectors.toList()));
        }
        schedule.setSubject(subjectMapper.toEntity(dto.subjectDTO()));

        return schedule;
    }
}