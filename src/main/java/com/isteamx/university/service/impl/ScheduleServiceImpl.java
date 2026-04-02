package com.isteamx.university.service.impl;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
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
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("Professor Not Found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));

        Group group = groupRepository.findById(createScheduleRequestDTO.groupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));


        if(scheduleRepository.existsByGroupAndStartingHourAndScheduleDayAndFrequency(group, createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency())) {
            throw new ResourceNotFoundException("This group already has a subject at that specific hour");
        }

        if(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(professor, createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency())) {
            throw new ResourceNotFoundException("This professor already has a subject at that specific hour");
        }


        List<Schedule> existingSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHour(room,createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.startingHour());

        Frequency newFrequency = createScheduleRequestDTO.frequency();

        for(Schedule schedule : existingSchedules){
            Frequency existingFrequency = schedule.getFrequency();

            if (existingFrequency == Frequency.SAPTAMANAL || newFrequency == Frequency.SAPTAMANAL) {
                throw new ResourceNotFoundException("The Room was already occupied");
            }

            if(existingFrequency == newFrequency){
                throw new ResourceNotFoundException("The Room was already occupied");
            }

        }


        Schedule schedule =  new Schedule();
        schedule.setStartingHour(createScheduleRequestDTO.startingHour());
        schedule.setEndingHour(createScheduleRequestDTO.endingHour());
        schedule.setScheduleDay(createScheduleRequestDTO.scheduleDay());
        schedule.setFrequency(createScheduleRequestDTO.frequency());
        schedule.setProfessor(professor);
        schedule.setRoom(room);
        schedule.setGroup(group);
        schedule.setSubject(subject);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return scheduleDTOMapper.toDTO(savedSchedule);
    }


    @Override
    @Transactional
    public void updateSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {

        Schedule schedule = scheduleRepository.findById(createScheduleRequestDTO.id()).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));

        Professor professor = professorRepository.findById(createScheduleRequestDTO.professorId()).orElseThrow(() -> new ResourceNotFoundException("The professor you're looking for was not found"));

        Room room = roomRepository.findById(createScheduleRequestDTO.roomId()).orElseThrow(() -> new ResourceNotFoundException("The Room you're looking for was not found"));

        Group group = groupRepository.findById(createScheduleRequestDTO.groupId()).orElseThrow(() -> new ResourceNotFoundException("The Group you're looking for was not found"));

        Subject subject = subjectRepository.findById(createScheduleRequestDTO.subjectId()).orElseThrow(() -> new ResourceNotFoundException("The Subject you're looking for was not found"));


        if(scheduleRepository.existsByGroupAndStartingHourAndScheduleDayAndFrequencyAndIdNot(group,createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency(),createScheduleRequestDTO.id())) {
            throw new AlreadyExistsException("This group already has a subject at that specific hour");
        }

        if(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndIdNot(professor,createScheduleRequestDTO.startingHour(),createScheduleRequestDTO.scheduleDay(),createScheduleRequestDTO.frequency(),createScheduleRequestDTO.id())) {
            throw new AlreadyExistsException("The professor already has a Schedule at that specific hour");
        }

        List<Schedule> existingSchedules = scheduleRepository.findByRoomAndScheduleDayAndStartingHour(room,createScheduleRequestDTO.scheduleDay(),schedule.getStartingHour());

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
        schedule.setGroup(group);
        schedule.setSubject(subject);


        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The schedule you're looking for was not found"));
        scheduleRepository.delete(schedule);
    }

}
