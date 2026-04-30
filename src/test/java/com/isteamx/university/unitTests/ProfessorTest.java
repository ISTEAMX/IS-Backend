package com.isteamx.university.unitTests;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.ProfessorRepository;
import com.isteamx.university.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorTest {

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    ProfessorDTOMapper professorDTOMapper;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @Test
    public void shouldGetProfessor() {
        Long id = 1L;

        Professor professor = new Professor();
        professor.setId(id);
        professor.setFirstName("test");

        ProfessorDTO professorDTO = new ProfessorDTO(id, "test", null, null);

        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));
        when(professorDTOMapper.toDTO(professor)).thenReturn(professorDTO);

        ProfessorDTO response = professorService.getProfessor(id);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(professorDTO.id());
    }

    @Test
    public void shouldGetAllProfessors() {
        Professor prof1 = new Professor();
        prof1.setId(1L);
        prof1.setFirstName("Lukas");

        User user1 = new User();
        user1.setId(1L);
        user1.setRole("PROFESSOR");
        prof1.setUser(user1);

        Professor prof2 = new Professor();
        prof2.setId(2L);
        prof2.setFirstName("Zozo");

        User user2 = new User();
        user2.setId(2L);
        user2.setRole("PROFESSOR");
        prof2.setUser(user2);

        ProfessorDTO dto1 = new ProfessorDTO(1L, "Lukas", null, null);
        ProfessorDTO dto2 = new ProfessorDTO(2L, "Zozo", null, null);

        Pageable pageable = PageRequest.of(0, 50);
        Page<Professor> professorPage = new PageImpl<>(List.of(prof1, prof2), pageable, 2);

        when(professorRepository.findAllByUserRole("PROFESSOR", pageable)).thenReturn(professorPage);
        when(professorDTOMapper.toDTO(prof1)).thenReturn(dto1);
        when(professorDTOMapper.toDTO(prof2)).thenReturn(dto2);

        Page<ProfessorDTO> response = professorService.getProfessors(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).firstName()).isEqualTo("Lukas");
        assertThat(response.getContent().get(1).firstName()).isEqualTo("Zozo");
    }

    @Test
    public void shouldThrowExceptionWhenProfessorNotFound() {
        Long id = 99L;
        when(professorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> professorService.getProfessor(id));
    }

    @Test
    public void shouldUpdateProfessor() {
        ProfessorDTO professorDTO = new ProfessorDTO(1L, "Updated", "Name", "NewDepartment");

        Professor existingProfessor = new Professor();
        existingProfessor.setId(1L);
        existingProfessor.setFirstName("Old");
        existingProfessor.setLastName("Name");
        existingProfessor.setDepartment("OldDepartment");

        Professor savedProfessor = new Professor();
        savedProfessor.setId(1L);
        savedProfessor.setFirstName("Updated");
        savedProfessor.setLastName("Name");
        savedProfessor.setDepartment("NewDepartment");

        ProfessorDTO savedDTO = new ProfessorDTO(1L, "Updated", "Name", "NewDepartment");

        when(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor));
        when(professorRepository.save(existingProfessor)).thenReturn(savedProfessor);
        when(professorDTOMapper.toDTO(savedProfessor)).thenReturn(savedDTO);

        professorService.updateProfessor(professorDTO);

        verify(professorRepository).save(existingProfessor);
        assertThat(existingProfessor.getFirstName()).isEqualTo("Updated");
        assertThat(existingProfessor.getDepartment()).isEqualTo("NewDepartment");
    }

    @Test
    public void shouldThrowExceptionWhenUpdateProfessorNotFound() {
        ProfessorDTO professorDTO = new ProfessorDTO(99L, "test", "test", "dept");
        when(professorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> professorService.updateProfessor(professorDTO));
    }

    @Test
    public void shouldDeleteProfessor() {
        Long id = 1L;
        Professor professor = new Professor();
        professor.setId(id);
        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));

        professorService.deleteProfessor(id);

        verify(professorRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteProfessorNotFound() {
        Long id = 99L;
        when(professorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> professorService.deleteProfessor(id));
    }
}