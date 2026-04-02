package com.isteamx.university.unitTests;


import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.ScheduleDTOMapper;
import com.isteamx.university.entity.*;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.*;
import com.isteamx.university.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScheduleTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleDTOMapper scheduleDTOMapper;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void shouldGetScheduleById() {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(1L);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(1L);

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(1L);

        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,"Monday","00:08","00:10", Frequency.PARA,professorDTO ,roomDTO,groupDTO,subjectDTO);
        Schedule schedule = new Schedule();

        when(scheduleRepository.findById(scheduleDTO.id())).thenReturn(Optional.of(schedule));
        when(scheduleDTOMapper.toDTO(schedule)).thenReturn(scheduleDTO);

        ScheduleDTO response = scheduleService.getSchedule(scheduleDTO.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(scheduleDTO.id());



    }

    @Test
    public void shouldGetAllSchedules() {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(1L);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(1L);

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(1L);

        ProfessorDTO professorDTO2 = new ProfessorDTO();
        professorDTO2.setId(2L);

        RoomDTO roomDTO2 = new RoomDTO();
        roomDTO2.setId(2L);

        GroupDTO groupDTO2 = new GroupDTO();
        groupDTO2.setId(2L);

        SubjectDTO subjectDTO2 = new SubjectDTO();
        subjectDTO2.setId(2L);

        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,"Monday","00:08","00:10", Frequency.PARA,professorDTO ,roomDTO,groupDTO,subjectDTO);
        ScheduleDTO scheduleDTO2 = new ScheduleDTO(2L,"Monday","08:00","00:10", Frequency.PARA,professorDTO2 ,roomDTO2,groupDTO2,subjectDTO2);


        Schedule schedule = new Schedule();
        schedule.setId(1L);
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        List<Schedule> list = List.of(schedule,schedule2);

        when(scheduleRepository.findAll()).thenReturn(list);
        when(scheduleDTOMapper.toDTO(schedule)).thenReturn(scheduleDTO);
        when(scheduleDTOMapper.toDTO(schedule2)).thenReturn(scheduleDTO2);

        List<ScheduleDTO> response = scheduleService.getSchedules();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);

    }

    @Test
    public void shouldSaveSchedule() {

        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null,"Monday","08:00","10:00", Frequency.PARA,professor.getId() ,room.getId(),group.getId(),subject.getId());


        Schedule savedSchedule = new Schedule();
        savedSchedule.setId(100L);

        ScheduleDTO expectedResponse = new ScheduleDTO(
                100L, "Monday", "08:00", "10:00", Frequency.PARA,
                new ProfessorDTO(), new RoomDTO(), new GroupDTO(), new SubjectDTO());

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        when(scheduleRepository.existsByGroupAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.PARA)).thenReturn(false);

        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(
                professor, "08:00", "Monday", Frequency.PARA)).thenReturn(false);

        when(scheduleRepository.findByRoomAndScheduleDayAndStartingHour(
                room, "Monday", "08:00")).thenReturn(List.of());


        when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);
        when(scheduleDTOMapper.toDTO(savedSchedule)).thenReturn(expectedResponse);

        ScheduleDTO response = scheduleService.addSchedule(inp);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(100L);


    }

    @Test
    public void shouldThrowExceptionWhenScheduleNotFound() {

        Long scheduleId = 99L;
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> scheduleService.getSchedule(scheduleId)
        );
    }

}
