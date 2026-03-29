package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomDTOMapper {
    public Room toEntity(RoomDTO roomDTO) {
        return new Room(
          roomDTO.getId(),
          roomDTO.getName(),
          roomDTO.getCapacity(),
          roomDTO.getType(),
          roomDTO.getLocation(),
          roomDTO.getEquipment(),
          roomDTO.getSchedule()
        );
    }

    public RoomDTO toDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getType(),
                room.getLocation(),
                room.getEquipment(),
                room.getSchedule()
        );
    }
}
