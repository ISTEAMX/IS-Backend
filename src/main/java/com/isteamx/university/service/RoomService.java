package com.isteamx.university.service;

import com.isteamx.university.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO getRoom(Long id);
    List<RoomDTO> getRooms();
    void updateRoom(RoomDTO roomDTO);
    void deleteRoom(Long id);
}
