package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoomDTOMapper {
    public Room toEntity(RoomDTO roomDTO) {
        return Optional.ofNullable(roomDTO).map(n -> {
            Room room = new Room();
            room.setId(n.getId());
            room.setName(n.getName());
            room.setType(n.getType());
            room.setLocation(n.getLocation());

            return room;

        }).orElse(null);
    }

    public RoomDTO toDTO(Room room) {
        return Optional.ofNullable(room).map(e -> {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(e.getId());
            roomDTO.setName(e.getName());
            roomDTO.setType(e.getType());
            roomDTO.setLocation(e.getLocation());

            return roomDTO;
        }).orElse(null);
    }
}
