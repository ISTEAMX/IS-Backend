package com.isteamx.university.service.impl;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.dtoMapper.RoomDTOMapper;
import com.isteamx.university.entity.Room;
import com.isteamx.university.repository.RoomRepository;
import com.isteamx.university.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomDTOMapper roomDTOMapper;

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setType(roomDTO.getType());
        room.setLocation(roomDTO.getLocation());
        room.setEquipment(roomDTO.getEquipment());
        room.setSchedule(roomDTO.getSchedule());

        Room savedRoom =  roomRepository.save(room);

        return roomDTOMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO getRoom(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(()->new RuntimeException("Room not found"));

        return roomDTOMapper.toDTO(room);
    }

    @Override
    public List<RoomDTO> getRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(roomDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void updateRoom(RoomDTO roomDTO) {
        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(()->new RuntimeException("Room not found"));
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setType(roomDTO.getType());
        room.setLocation(roomDTO.getLocation());
        room.setEquipment(roomDTO.getEquipment());
        room.setSchedule(roomDTO.getSchedule());
        roomRepository.save(room);

    }

    @Override
    public void deleteRoom(Long id) {
    roomRepository.deleteById(id);
    }
}
