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
        professor.setFirstName(dto.getFirstName());
        professor.setLastName(dto.getLastName());
        professor.setDepartment(dto.getDepartment());
        return professor;
    }

    public ProfessorDTO toDTO(Professor professor) {
        if (professor == null) {
            return null;
        }
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setFirstName(professor.getFirstName());
        dto.setLastName(professor.getLastName());
        dto.setDepartment(professor.getDepartment());
        return dto;
    }
}
