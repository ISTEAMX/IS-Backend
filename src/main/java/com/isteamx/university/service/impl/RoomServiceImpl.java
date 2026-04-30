package com.isteamx.university.service.impl;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.dtoMapper.RoomDTOMapper;
import com.isteamx.university.entity.Room;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.RoomRepository;
import com.isteamx.university.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomDTOMapper roomDTOMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO) {

        if(roomRepository.existsByNameOrLocation(roomDTO.name(),roomDTO.location())){
            throw new AlreadyExistsException("Room with that name or location already exists");
        }

        Room room = new Room();
        room.setName(roomDTO.name());
        room.setCapacity(roomDTO.capacity());
        room.setType(roomDTO.type());
        room.setLocation(roomDTO.location());


        Room savedRoom =  roomRepository.save(room);

        return roomDTOMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO getRoom(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Room not found"));

        return roomDTOMapper.toDTO(room);
    }

    @Override
    public Page<RoomDTO> getRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(roomDTOMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void updateRoom(RoomDTO roomDTO) {

        if(roomRepository.existsByNameOrLocationExcludingId(roomDTO.name(),roomDTO.location(),roomDTO.id())){
            throw new AlreadyExistsException("Sorry you can't update the room into one that already exists");
        }

        Room room = roomRepository.findById(roomDTO.id()).orElseThrow(()->new ResourceNotFoundException("Room not found"));
        room.setName(roomDTO.name());
        room.setCapacity(roomDTO.capacity());
        room.setType(roomDTO.type());
        room.setLocation(roomDTO.location());
        roomRepository.save(room);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @Transactional
    public void deleteRoom(Long id) {
        if(roomRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Room with id " + id + " doesn't exist");
        }

    roomRepository.deleteById(id);
    }


}
