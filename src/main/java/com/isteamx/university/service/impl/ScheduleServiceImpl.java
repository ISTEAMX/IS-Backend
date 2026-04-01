package com.isteamx.university.service.impl;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.dtoMapper.ScheduleDTOMapper;
import com.isteamx.university.entity.*;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.*;
import com.isteamx.university.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfessorRepository professorRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final ScheduleDTOMapper scheduleDTOMapper;


    @Override
    @Transactional
    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("Professor Not Found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));

        Group group = groupRepository.findById(createScheduleRequestDTO.groupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if(scheduleRepository.existsByRoomAndScheduleDayAndStartingHour(room,createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.startingHour())){
        throw new ResourceNotFoundException("Schedule already exists");
        }

        Schedule schedule =  new Schedule();
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setProfessor(professor);
        schedule.setRoom(room);
        schedule.setGroup(group);
        schedule.setSubject(subject);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleDTOMapper.toDTO(savedSchedule);
    }


}
