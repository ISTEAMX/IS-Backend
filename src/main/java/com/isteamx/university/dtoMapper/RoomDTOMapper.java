package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomDTOMapper {

    public Room toEntity(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }

        Room room = new Room();
        room.setId(roomDTO.id());
        room.setName(roomDTO.name());
        room.setCapacity(roomDTO.capacity());
        room.setType(roomDTO.type());
        room.setLocation(roomDTO.location());
        return room;
    }

    public RoomDTO toDTO(Room room) {
        if (room == null) {
            return null;
        }

        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getType(),
                room.getLocation()
        );
    }

}