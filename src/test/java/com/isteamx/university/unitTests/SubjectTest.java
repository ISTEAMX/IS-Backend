package com.isteamx.university.unitTests;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.dtoMapper.SubjectDTOMapper;
import com.isteamx.university.entity.Subject;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.SubjectRepository;
import com.isteamx.university.service.impl.SubjectServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectTest {

    @Mock
    private SubjectDTOMapper subjectDTOMapper;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    public void shouldGetSubjectById() {
        Long id = 1L;

        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "Lab");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("test");

        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));
        when(subjectDTOMapper.toDTO(subject)).thenReturn(subjectDTO);

        SubjectDTO resp = subjectService.getSubjectById(id);

        assertThat(resp).isNotNull();
        assertThat(resp.id()).isEqualTo(subjectDTO.id());
    }

    @Test
    public void shouldGetAllSubjects() {
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("test");

        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("test2");

        List<Subject> list = List.of(subject1, subject2);

        SubjectDTO subjectDTO1 = new SubjectDTO(1L, "test", "Lab");
        SubjectDTO subjectDTO2 = new SubjectDTO(2L, "test2", "Lab");

        when(subjectRepository.findAll()).thenReturn(list);
        when(subjectDTOMapper.toDTO(subject1)).thenReturn(subjectDTO1);
        when(subjectDTOMapper.toDTO(subject2)).thenReturn(subjectDTO2);

        List<SubjectDTO> resp = subjectService.getSubjects();

        assertThat(resp).isNotNull();
        assertThat(resp.size()).isEqualTo(2);
    }

    @Test
    public void shouldCreateSubject() {
        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "lab");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("test");
        subject.setActivityType("lab");

        SubjectDTO mappedSubjectDTO = new SubjectDTO(1L, "test", "lab");

        Subject savedSubject = new Subject();
        savedSubject.setId(1L);
        savedSubject.setName("test");
        savedSubject.setActivityType("lab");

        when(subjectRepository.existsByNameAndActivityType(subjectDTO.name(), subjectDTO.activityType())).thenReturn(false);
        when(subjectDTOMapper.toEntity(subjectDTO)).thenReturn(subject);
        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);
        when(subjectDTOMapper.toDTO(savedSubject)).thenReturn(mappedSubjectDTO);

        SubjectDTO resp = subjectService.createSubject(subjectDTO);

        assertThat(resp).isNotNull();
        assertThat(resp.id()).isEqualTo(subjectDTO.id());
    }

    @Test
    public void shouldThrowExceptionWhenSubjectNotFound() {
        Long id = 99L;
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.getSubjectById(id));
    }

    @Test
    public void shouldThrowExceptionWhenCreateSubjectAlreadyExists() {
        SubjectDTO subjectDTO = new SubjectDTO(1L, "test", "Lab");

        when(subjectRepository.existsByNameAndActivityType(subjectDTO.name(), subjectDTO.activityType())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> subjectService.createSubject(subjectDTO));
    }

    @Test
    public void shouldUpdateSubject() {
        SubjectDTO subjectDTO = new SubjectDTO(1L, "updated", "Seminar");

        Subject existingSubject = new Subject();
        existingSubject.setId(1L);
        existingSubject.setName("test");
        existingSubject.setActivityType("Lab");

        when(subjectRepository.findById(subjectDTO.id())).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.existsByNameAndActivityTypeAndIdNot(subjectDTO.name(), subjectDTO.activityType(), subjectDTO.id())).thenReturn(false);
        when(subjectRepository.save(existingSubject)).thenReturn(existingSubject);

        subjectService.updateSubject(subjectDTO);

        verify(subjectRepository).save(existingSubject);
        assertThat(existingSubject.getName()).isEqualTo("updated");
        assertThat(existingSubject.getActivityType()).isEqualTo("Seminar");
    }

    @Test
    public void shouldThrowExceptionWhenUpdateSubjectNotFound() {
        SubjectDTO subjectDTO = new SubjectDTO(99L, "test", "Lab");

        when(subjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.updateSubject(subjectDTO));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateSubjectAlreadyExists() {
        SubjectDTO subjectDTO = new SubjectDTO(1L, "duplicate", "Lab");

        Subject existingSubject = new Subject();
        existingSubject.setId(1L);

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.existsByNameAndActivityTypeAndIdNot("duplicate", "Lab", 1L)).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> subjectService.updateSubject(subjectDTO));
    }

    @Test
    public void shouldDeleteSubject() {
        Long id = 1L;
        Subject subject = new Subject();
        subject.setId(id);

        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        subjectService.deleteSubject(id);

        verify(subjectRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteSubjectNotFound() {
        Long id = 99L;
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.deleteSubject(id));
    }
}