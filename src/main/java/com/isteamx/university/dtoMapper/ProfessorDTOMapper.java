package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.entity.Professor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfessorDTOMapper {

    public Professor toEntity(ProfessorDTO dto) {
        return Optional.ofNullable(dto).map(d -> {
            Professor professor = new Professor();
            professor.setFirstName(d.firstName());
            professor.setLastName(d.lastName());
            professor.setDepartment(d.department());
            return professor;
        }).orElse(null);
    }

    public ProfessorDTO toDTO(Professor professor) {
       return Optional.ofNullable(professor).map(e ->
                       new ProfessorDTO(professor.getId(),professor.getFirstName(),professor.getLastName(),professor.getDepartment()))
               .orElse(null);
    }
}
