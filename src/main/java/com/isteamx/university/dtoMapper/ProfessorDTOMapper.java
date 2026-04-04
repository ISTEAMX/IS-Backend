package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorDTOMapper {

    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) {
            return null;
        }

        Professor professor = new Professor();
        professor.setId(dto.id());
        professor.setFirstName(dto.firstName());
        professor.setLastName(dto.lastName());
        professor.setDepartment(dto.department());
        return professor;
    }

    public ProfessorDTO toDTO(Professor professor) {
        if (professor == null) {
            return null;
        }

        return new ProfessorDTO(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getDepartment()
        );
    }
}