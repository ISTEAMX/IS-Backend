package com.isteamx.university.dtoMapper;
import com.isteamx.university.dto.UserDTO;
import com.isteamx.university.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDTOMapper {

    public final ProfessorDTOMapper professorDTOMapper;

    public UserDTO toDTO(User entity) {

        return Optional.ofNullable(entity).map(e -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(e.getEmail());
            userDTO.setFirstName(e.getFirstName());
            userDTO.setLastName(e.getLastName());
            userDTO.setPassword(e.getPassword());
            userDTO.setRole(e.getRole());
            userDTO.setProfessor(Optional.ofNullable(e.getProfessor()).map(professorDTOMapper::toDTO).orElse(null));

            return userDTO;

        }).orElse(null);

    }

    public User toEntity(UserDTO dto) {
        return Optional.ofNullable(dto).map( d -> {
            User user = new User();
            user.setEmail(d.getEmail());
            user.setFirstName(d.getFirstName());
            user.setLastName(d.getLastName());
            user.setPassword(d.getPassword());
            user.setRole(d.getRole());
            user.setProfessor(Optional.ofNullable(d.getProfessor()).map( professorDTOMapper::toEntity).orElse(null));

            return user;

        }).orElse(null);
    }
}
