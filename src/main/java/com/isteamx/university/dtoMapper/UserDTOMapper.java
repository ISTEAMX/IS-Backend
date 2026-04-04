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
        return Optional.ofNullable(entity).map(e -> new UserDTO(
                e.getId(),
                e.getEmail(),
                e.getFirstName(),
                e.getLastName(),
                e.getPassword(),
                e.getRole(),
                Optional.ofNullable(e.getProfessor()).map(professorDTOMapper::toDTO).orElse(null)
        )).orElse(null);
    }

    public User toEntity(UserDTO dto) {
        return Optional.ofNullable(dto).map( d -> {
            User user = new User();
            user.setEmail(d.email());
            user.setFirstName(d.firstName());
            user.setLastName(d.lastName());
            user.setPassword(d.password());
            user.setRole(d.role());
            user.setProfessor(Optional.ofNullable(d.professor()).map( professorDTOMapper::toEntity).orElse(null));

            return user;

        }).orElse(null);
    }
}
