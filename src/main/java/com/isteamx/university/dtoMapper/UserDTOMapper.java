package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDTOMapper {

    public final ProfessorDTOMapper professorDTOMapper;

    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());

        if(entity.getProfessor() != null) {
            ProfessorDTO professor = professorDTOMapper.toDTO(entity.getProfessor());
            dto.setProfessor(professor);
        }

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        if(dto.getProfessor() != null) {
            Professor professor = professorDTOMapper.toEntity(dto.getProfessor());
            professor.setUser(user);
            user.setProfessor(professor);
        }

        return user;
    }
}
