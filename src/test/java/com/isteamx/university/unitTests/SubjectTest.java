package com.isteamx.university.unitTests;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.dtoMapper.SubjectDTOMapper;
import com.isteamx.university.entity.Subject;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}