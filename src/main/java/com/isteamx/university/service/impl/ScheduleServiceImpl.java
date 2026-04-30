package com.isteamx.university.service.impl;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.dtoMapper.ScheduleDTOMapper;
import com.isteamx.university.entity.*;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.*;
import com.isteamx.university.service.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ScheduleDTO getSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        return scheduleDTOMapper.toDTO(schedule);
    }

    @Override
    public List<ScheduleDTO> getSchedules() {

        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream().map(scheduleDTOMapper::toDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("Professor Not Found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        List<Group> groups = new ArrayList<>();
        if (createScheduleRequestDTO.groupIds() != null && !createScheduleRequestDTO.groupIds().isEmpty()) {
            for (Long groupId : createScheduleRequestDTO.groupIds()) {
                Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
                groups.add(group);
            }
        }

        for (Group group : groups) {
            if (scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(group, createScheduleRequestDTO.startingHour(), createScheduleRequestDTO.scheduleDay(), createScheduleRequestDTO.frequency())) {
                throw new AlreadyExistsException("Group " + group.getIdentifier() + " already has a subject at that specific hour");
            }
        }

        if(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(professor, createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency())) {
            throw new AlreadyExistsException("This professor already has a subject at that specific hour");
        }


        List<Schedule> existingSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHour(room,createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.startingHour());

        Frequency newFrequency = createScheduleRequestDTO.frequency();

        for(Schedule schedule : existingSchedules){
            Frequency existingFrequency = schedule.getFrequency();

            if (existingFrequency == Frequency.SAPTAMANAL || newFrequency == Frequency.SAPTAMANAL) {
                throw new AlreadyExistsException("The Room was already occupied");
            }

            if(existingFrequency == newFrequency){
                throw new AlreadyExistsException("The Room was already occupied");
            }

        }


        Schedule schedule =  new Schedule();
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setFrequency(createScheduleRequestDTO.frequency());
        schedule.setProfessor(professor);
        schedule.setRoom(room);
        schedule.setGroups(groups);
        schedule.setSubject(subject);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleDTOMapper.toDTO(savedSchedule);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void updateSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Schedule schedule = scheduleRepository.findById(createScheduleRequestDTO.id()).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("The professor you're looking for was not found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("The Room you're looking for was not found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("The Subject you're looking for was not found"));

        List<Group> groups = new ArrayList<>();
        if (createScheduleRequestDTO.groupIds() != null && !createScheduleRequestDTO.groupIds().isEmpty()) {
            for (Long groupId : createScheduleRequestDTO.groupIds()) {
                Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
                groups.add(group);
            }
        }

        for (Group group : groups) {
            if (scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndIdNot(group, createScheduleRequestDTO.startingHour(), createScheduleRequestDTO.scheduleDay(), createScheduleRequestDTO.frequency(), createScheduleRequestDTO.id())) {
                throw new AlreadyExistsException("Group " + group.getIdentifier() + " already has a subject at that specific hour");
            }
        }

        if(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndIdNot(professor,createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency(),createScheduleRequestDTO.id())) {
            throw new AlreadyExistsException("The professor already has a Schedule at that specific hour");
        }

        // BUG FIX: Use the NEW startingHour from the DTO, not the old one from the DB entity
        List<Schedule> existingSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHour(room,createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.startingHour());

        Frequency newFrequency = createScheduleRequestDTO.frequency();

        for(Schedule existing : existingSchedules){

            if (existing.getId().equals(schedule.getId())) continue;

            Frequency existingFrequency = existing.getFrequency();
            if(existingFrequency == Frequency.SAPTAMANAL || newFrequency == Frequency.SAPTAMANAL) {
                throw new AlreadyExistsException("The Schedule was already occupied");
            }

            if(existingFrequency == newFrequency){
                throw new AlreadyExistsException("The Schedule was already occupied");
            }
        }
        
        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setFrequency(createScheduleRequestDTO.frequency());
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setRoom(room);
        schedule.setProfessor(professor);
        schedule.setGroups(groups);
        schedule.setSubject(subject);


        scheduleRepository.save(schedule);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<ScheduleDTO> getSchedulesByFilters(FilterDTO filterDTO) {
        List<Schedule> filteredSchedules = scheduleRepository.findSchedulesByDynamicFilters(filterDTO.professorId(),filterDTO.roomId(),filterDTO.groupId(),filterDTO.subjectId(),filterDTO.scheduleDay(),filterDTO.frequency());

        return filteredSchedules.stream().map(scheduleDTOMapper::toDTO).collect(Collectors.toList());
    }

}
