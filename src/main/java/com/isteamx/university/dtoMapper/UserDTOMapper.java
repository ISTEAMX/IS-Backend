package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDTOMapper {

    private final ProfessorDTOMapper professorDTOMapper;

    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        // NOTE: Password is intentionally NOT mapped to DTO to prevent leakage
        dto.setRole(entity.getRole());

        if (entity.getProfessor() != null) {
            ProfessorDTO professor = professorDTOMapper.toDTO(entity.getProfessor());
            dto.setProfessor(professor);
        }

        return dto;
    }
}
