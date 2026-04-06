package com.isteamx.university.dtoMapper;

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

        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                professorDTOMapper.toDTO(entity.getProfessor())
        );
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.id());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setProfessor(professorDTOMapper.toEntity(dto.professor()));
        return user;
    }

}