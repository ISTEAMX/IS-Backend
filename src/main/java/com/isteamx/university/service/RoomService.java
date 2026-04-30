package com.isteamx.university.service;

import com.isteamx.university.dto.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO getRoom(Long id);
    Page<RoomDTO> getRooms(Pageable pageable);
    void updateRoom(RoomDTO roomDTO);
    void deleteRoom(Long id);
}
