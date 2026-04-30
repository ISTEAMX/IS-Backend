package com.isteamx.university.unitTests;


import com.isteamx.university.dto.*;
import com.isteamx.university.dtoMapper.ScheduleDTOMapper;
import com.isteamx.university.entity.*;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.*;
import com.isteamx.university.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        ProfessorDTO professorDTO = new ProfessorDTO(1L, "test", "test", "test");


        RoomDTO roomDTO = new RoomDTO(1L, "T204",60,"Lab","T");


        GroupDTO groupDTO = new GroupDTO(1L, "group1", "TI",3, 1);


        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "Lab");


        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,"Monday","00:08","00:10", Frequency.PARA,professorDTO ,roomDTO,List.of(groupDTO),subjectDTO);
        Schedule schedule = new Schedule();

        when(scheduleRepository.findById(scheduleDTO.id())).thenReturn(Optional.of(schedule));
        when(scheduleDTOMapper.toDTO(schedule)).thenReturn(scheduleDTO);

        ScheduleDTO response = scheduleService.getSchedule(scheduleDTO.id());

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(scheduleDTO.id());



    }

    @Test
    public void shouldGetAllSchedules() {


        ProfessorDTO professorDTO = new ProfessorDTO(1L, "test", "test", "test");
        RoomDTO roomDTO = new RoomDTO(1L, "T204",60,"Lab","T");
        GroupDTO groupDTO = new GroupDTO(1L, "group1", "TI",3, 1);
        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "Lab");

        ProfessorDTO professorDTO2 = new ProfessorDTO(2L, "test", "test", "test");
        RoomDTO roomDTO2 = new RoomDTO(2L, "T204",60,"Lab","T");
        GroupDTO groupDTO2 = new GroupDTO(2L, "group1", "TI",3, 1);
        SubjectDTO subjectDTO2 = new SubjectDTO(2L, "test", "Lab");

        ScheduleDTO scheduleDTO = new ScheduleDTO(1L,"Monday","00:08","00:10", Frequency.PARA,professorDTO ,roomDTO,List.of(groupDTO),subjectDTO);
        ScheduleDTO scheduleDTO2 = new ScheduleDTO(2L,"Monday","08:00","00:10", Frequency.PARA,professorDTO2 ,roomDTO2,List.of(groupDTO2),subjectDTO2);


        Schedule schedule = new Schedule();
        schedule.setId(1L);
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);

        Pageable pageable = PageRequest.of(0, 50);
        Page<Schedule> schedulePage = new PageImpl<>(List.of(schedule, schedule2), pageable, 2);

        when(scheduleRepository.findAll(pageable)).thenReturn(schedulePage);
        when(scheduleDTOMapper.toDTO(schedule)).thenReturn(scheduleDTO);
        when(scheduleDTOMapper.toDTO(schedule2)).thenReturn(scheduleDTO2);

        Page<ScheduleDTO> response = scheduleService.getSchedules(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);

    }

    @Test
    public void shouldSaveSchedule() {

        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null,"Monday","08:00","10:00", Frequency.PARA,professor.getId() ,room.getId(),List.of(group.getId()),subject.getId());


        Schedule savedSchedule = new Schedule();
        savedSchedule.setId(100L);

        ScheduleDTO expectedResponse = new ScheduleDTO(
                100L, "Monday", "08:00", "10:00", Frequency.PARA,
                new ProfessorDTO(1L, "test", "test", "test"),
                new RoomDTO(1L, "T204",60,"Lab","T"),
                List.of(new GroupDTO(1L, "group1", "TI",3, 1)),
                new SubjectDTO(1L, "test", "Lab")
        );

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
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

    @Test
    public void shouldThrowExceptionWhenAddScheduleGroupConflict() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L); group.setIdentifier("G1");
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.PARA)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleProfessorConflict() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.PARA)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(
                professor, "08:00", "Monday", Frequency.PARA)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleRoomConflictWeekly() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.SAPTAMANAL, 1L, 1L, List.of(1L), 1L);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(50L);
        existingSchedule.setFrequency(Frequency.PARA);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.SAPTAMANAL)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(
                professor, "08:00", "Monday", Frequency.SAPTAMANAL)).thenReturn(false);
        when(scheduleRepository.findByRoomAndScheduleDayAndStartingHour(
                room, "Monday", "08:00")).thenReturn(List.of(existingSchedule));

        assertThrows(AlreadyExistsException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleRoomConflictSameFrequency() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(50L);
        existingSchedule.setFrequency(Frequency.PARA);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.PARA)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(
                professor, "08:00", "Monday", Frequency.PARA)).thenReturn(false);
        when(scheduleRepository.findByRoomAndScheduleDayAndStartingHour(
                room, "Monday", "08:00")).thenReturn(List.of(existingSchedule));

        assertThrows(AlreadyExistsException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleProfessorNotFound() {
        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleRoomNotFound() {
        Professor professor = new Professor(); professor.setId(1L);
        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleSubjectNotFound() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenAddScheduleGroupNotFound() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);
        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(99L), 1L);
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.addSchedule(inp));
    }

    @Test
    public void shouldDeleteSchedule() {
        Long id = 1L;
        Schedule schedule = new Schedule();
        schedule.setId(id);
        when(scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));

        scheduleService.deleteSchedule(id);

        verify(scheduleRepository).delete(schedule);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteScheduleNotFound() {
        Long id = 99L;
        when(scheduleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.deleteSchedule(id));
    }

    @Test
    public void shouldUpdateSchedule() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(1L);
        existingSchedule.setScheduleDay("Monday");
        existingSchedule.setStartingHour("08:00");

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(1L, "Tuesday", "10:00", "12:00", Frequency.SAPTAMANAL, 1L, 1L, List.of(1L), 1L);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(existingSchedule));
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndIdNot(
                group, "10:00", "Tuesday", Frequency.SAPTAMANAL, 1L)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndIdNot(
                professor, "10:00", "Tuesday", Frequency.SAPTAMANAL, 1L)).thenReturn(false);
        when(scheduleRepository.findByRoomAndScheduleDayAndStartingHour(
                room, "Tuesday", "10:00")).thenReturn(List.of());
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);

        scheduleService.updateSchedule(inp);

        verify(scheduleRepository).save(existingSchedule);
        assertThat(existingSchedule.getScheduleDay()).isEqualTo("Tuesday");
        assertThat(existingSchedule.getStartingHour()).isEqualTo("10:00");
        assertThat(existingSchedule.getEndingHour()).isEqualTo("12:00");
    }

    @Test
    public void shouldThrowExceptionWhenUpdateScheduleNotFound() {
        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(99L, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);
        when(scheduleRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> scheduleService.updateSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateScheduleGroupConflict() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L); group.setIdentifier("G1");
        Subject subject = new Subject(); subject.setId(1L);
        Schedule existing = new Schedule(); existing.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(1L, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndIdNot(
                group, "08:00", "Monday", Frequency.PARA, 1L)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> scheduleService.updateSchedule(inp));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateScheduleProfessorConflict() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);
        Schedule existing = new Schedule(); existing.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(1L, "Monday", "08:00", "10:00", Frequency.PARA, 1L, 1L, List.of(1L), 1L);

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndIdNot(
                group, "08:00", "Monday", Frequency.PARA, 1L)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndIdNot(
                professor, "08:00", "Monday", Frequency.PARA, 1L)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> scheduleService.updateSchedule(inp));
    }

    @Test
    public void shouldGetSchedulesByFilters() {
        FilterDTO filterDTO = new FilterDTO(1L, null, null, null, "Monday", null);

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        ProfessorDTO professorDTO = new ProfessorDTO(1L, "test", "test", "test");
        RoomDTO roomDTO = new RoomDTO(1L, "T204", 60, "Lab", "T");
        GroupDTO groupDTO = new GroupDTO(1L, "group1", "TI", 3, 1);
        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "Lab");
        ScheduleDTO scheduleDTO = new ScheduleDTO(1L, "Monday", "08:00", "10:00", Frequency.PARA, professorDTO, roomDTO, List.of(groupDTO), subjectDTO);

        Pageable pageable = PageRequest.of(0, 50);
        Page<Schedule> schedulePage = new PageImpl<>(List.of(schedule), pageable, 1);

        when(scheduleRepository.findSchedulesByDynamicFilters(1L, null, null, null, "Monday", null, pageable))
                .thenReturn(schedulePage);
        when(scheduleDTOMapper.toDTO(schedule)).thenReturn(scheduleDTO);

        Page<ScheduleDTO> response = scheduleService.getSchedulesByFilters(filterDTO, pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).scheduleDay()).isEqualTo("Monday");
    }

    @Test
    public void shouldAddScheduleWithDifferentFrequencyInSameRoomSlot() {
        Professor professor = new Professor(); professor.setId(1L);
        Room room = new Room(); room.setId(1L);
        Group group = new Group(); group.setId(1L);
        Subject subject = new Subject(); subject.setId(1L);

        CreateScheduleRequestDTO inp = new CreateScheduleRequestDTO(null, "Monday", "08:00", "10:00", Frequency.INPARA, 1L, 1L, List.of(1L), 1L);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(50L);
        existingSchedule.setFrequency(Frequency.PARA);

        Schedule savedSchedule = new Schedule();
        savedSchedule.setId(100L);

        ScheduleDTO expectedResponse = new ScheduleDTO(
                100L, "Monday", "08:00", "10:00", Frequency.INPARA,
                new ProfessorDTO(1L, "test", "test", "test"),
                new RoomDTO(1L, "T204", 60, "Lab", "T"),
                List.of(new GroupDTO(1L, "group1", "TI", 3, 1)),
                new SubjectDTO(1L, "test", "Lab")
        );

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scheduleRepository.existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(
                group, "08:00", "Monday", Frequency.INPARA)).thenReturn(false);
        when(scheduleRepository.existsByProfessorAndStartingHourAndScheduleDayAndFrequency(
                professor, "08:00", "Monday", Frequency.INPARA)).thenReturn(false);
        when(scheduleRepository.findByRoomAndScheduleDayAndStartingHour(
                room, "Monday", "08:00")).thenReturn(List.of(existingSchedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(savedSchedule);
        when(scheduleDTOMapper.toDTO(savedSchedule)).thenReturn(expectedResponse);

        ScheduleDTO response = scheduleService.addSchedule(inp);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(100L);
    }
}
