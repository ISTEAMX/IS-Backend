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
            room.setId(n.id());
            room.setName(n.name());
            room.setCapacity(n.capacity());
            room.setType(n.type());
            room.setLocation(n.location());

            return room;

        }).orElse(null);
    }

    public RoomDTO toDTO(Room room) {
        return Optional.ofNullable(room).map(e -> new RoomDTO(
        e.getId(),
        e.getName(),
        e.getCapacity(),
        e.getType(),
        e.getLocation()
        )).orElse(null);
    }
}
