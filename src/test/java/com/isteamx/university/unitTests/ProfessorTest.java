package com.isteamx.university.unitTests;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import com.isteamx.university.repository.ProfessorRepository;
import com.isteamx.university.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

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

        List<Professor> mockProfessors = List.of(prof1, prof2);

        ProfessorDTO dto1 = new ProfessorDTO(1L, "Lukas", null, null);
        ProfessorDTO dto2 = new ProfessorDTO(2L, "Zozo", null, null);

        when(professorRepository.findAllByUserRole("PROFESSOR")).thenReturn(mockProfessors);
        when(professorDTOMapper.toDTO(prof1)).thenReturn(dto1);
        when(professorDTOMapper.toDTO(prof2)).thenReturn(dto2);

        List<ProfessorDTO> response = professorService.getProfessors();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).firstName()).isEqualTo("Lukas");
        assertThat(response.get(1).firstName()).isEqualTo("Zozo");
    }
}