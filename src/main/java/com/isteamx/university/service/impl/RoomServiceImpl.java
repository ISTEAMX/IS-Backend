package com.isteamx.university.service.impl;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.dtoMapper.RoomDTOMapper;
import com.isteamx.university.entity.Room;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.RoomRepository;
import com.isteamx.university.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomDTOMapper roomDTOMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO) {

        if(roomRepository.existsByNameOrLocation(roomDTO.getName(),roomDTO.getLocation())){
            throw new ResourceNotFoundException("Room with name already exists");
        }

        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setType(roomDTO.getType());
        room.setLocation(roomDTO.getLocation());


        Room savedRoom =  roomRepository.save(room);

        return roomDTOMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO getRoom(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Room not found"));

        return roomDTOMapper.toDTO(room);
    }

    @Override
    public List<RoomDTO> getRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(roomDTOMapper::toDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void updateRoom(RoomDTO roomDTO) {

        if(roomRepository.existsByNameOrLocationAndIdNot(roomDTO.getLocation(),roomDTO.getName(),roomDTO.getId())){
            throw new ResourceNotFoundException("Sorry you can't update the room into one that already exists");
        }

        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(()->new ResourceNotFoundException("Room not found"));
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setType(roomDTO.getType());
        room.setLocation(roomDTO.getLocation());
        roomRepository.save(room);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void deleteRoom(Long id) {
        if(roomRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Room with id " + id + " doesn't exist");
        }

    roomRepository.deleteById(id);
    }


}
