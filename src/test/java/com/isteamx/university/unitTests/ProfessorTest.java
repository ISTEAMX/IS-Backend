package com.isteamx.university.unitTests;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.entity.Professor;
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

        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(id);
        professorDTO.setFirstName("test");

        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));
        when(professorDTOMapper.toDTO(professor)).thenReturn(professorDTO);

        ProfessorDTO response = professorService.getProfessor(id);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(professorDTO.getId());
    }

    @Test
    public void shouldGetAllProfessors() {

        Professor prof1 = new Professor();
        prof1.setId(1L);
        prof1.setFirstName("Lukas");

        Professor prof2 = new Professor();
        prof2.setId(2L);
        prof2.setFirstName("Zozo");

        List<Professor> mockProfessors = List.of(prof1, prof2);

        ProfessorDTO dto1 = new ProfessorDTO();
        dto1.setId(1L);
        dto1.setFirstName("Lukas");

        ProfessorDTO dto2 = new ProfessorDTO();
        dto2.setId(2L);
        dto2.setFirstName("Zozo");

        when(professorRepository.findAll()).thenReturn(mockProfessors);
        when(professorDTOMapper.toDTO(prof1)).thenReturn(dto1);
        when(professorDTOMapper.toDTO(prof2)).thenReturn(dto2);

        List<ProfessorDTO> response = professorService.getProfessors();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getFirstName()).isEqualTo("Lukas");
        assertThat(response.get(1).getFirstName()).isEqualTo("Zozo");

    }
}
