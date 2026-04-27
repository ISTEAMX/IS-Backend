package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.GroupDTO;
import com.isteamx.university.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupDTOMapper {

    public GroupDTO toDTO(Group group) {
        if (group == null) {
            return null;
        }
        return new GroupDTO(
                group.getId(),
                group.getIdentifier(),
                group.getSpecialization(),
                group.getYear(),
                group.getSemester()
        );
    }

    public Group toEntity(GroupDTO groupDTO) {
        if (groupDTO == null) {
            return null;
        }
        return new Group(
                groupDTO.id(),
                groupDTO.identifier(),
                groupDTO.specialization(),
                groupDTO.year(),
                groupDTO.semester(),
                null
        );
    }
}