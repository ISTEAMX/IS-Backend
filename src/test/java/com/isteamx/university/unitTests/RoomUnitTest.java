package com.isteamx.university.unitTests;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.dtoMapper.RoomDTOMapper;
import com.isteamx.university.entity.Room;
import com.isteamx.university.repository.RoomRepository;
import com.isteamx.university.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomUnitTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomDTOMapper roomDTOMapper;

    @InjectMocks()
    private  RoomServiceImpl roomService;


    @Test
    public void shouldCreateRoom() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setCapacity(10);

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setCapacity(10);

        RoomDTO savedRoomDTO = new RoomDTO();
        savedRoomDTO.setId(1L);
        savedRoomDTO.setCapacity(10);

        when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);
        when(roomDTOMapper.toDTO(savedRoom)).thenReturn(savedRoomDTO);

        RoomDTO createdRoomDTO = roomService.createRoom(roomDTO);

        assertThat(createdRoomDTO.getId()).isNotNull();
        assertThat(createdRoomDTO.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetRoom() {
        Long id = 1L;

        Room room = new Room();
        room.setId(id);
        room.setCapacity(10);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setCapacity(10);

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomDTOMapper.toDTO(room)).thenReturn(roomDTO);

        RoomDTO createdRoomDTO = roomService.getRoom(id);

        assertThat(createdRoomDTO).isNotNull();
        assertThat(createdRoomDTO.getId()).isEqualTo(roomDTO.getId());

    }

    @Test
    public void shouldGetAllRooms() {

        Room room1 = new Room();
        room1.setId(1L);

        RoomDTO roomDTO1 = new RoomDTO();
        roomDTO1.setId(1L);

        Room room2 = new Room();
        room2.setId(2L);

        RoomDTO roomDTO2 = new RoomDTO();
        roomDTO2.setId(2L);

        List<Room> rooms = List.of(room1, room2);


        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomDTOMapper.toDTO(room1)).thenReturn(roomDTO1);
        when(roomDTOMapper.toDTO(room2)).thenReturn(roomDTO2);

        List<RoomDTO> response = roomService.getRooms();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(1).getId()).isEqualTo(2L);


    }


}
