package com.isteamx.university.service;

import com.isteamx.university.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO getRoom(Long id);
    List<RoomDTO> getRooms();
    void updateRoom(RoomDTO roomDTO);
    void deleteRoom(Long id);
}
