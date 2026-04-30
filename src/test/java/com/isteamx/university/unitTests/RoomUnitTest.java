package com.isteamx.university.unitTests;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.dtoMapper.RoomDTOMapper;
import com.isteamx.university.entity.Room;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.RoomRepository;
import com.isteamx.university.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomUnitTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomDTOMapper roomDTOMapper;

    @InjectMocks()
    private RoomServiceImpl roomService;

    @Test
    public void shouldCreateRoom() {
        RoomDTO roomDTO = new RoomDTO(1L, "T204",60,"Lab","T");

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setCapacity(10);

        RoomDTO savedRoomDTO = new RoomDTO(1L, "T204",60,"Lab","T");

        when(roomRepository.existsByNameOrLocation(roomDTO.name(), roomDTO.location())).thenReturn(false);
        when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);
        when(roomDTOMapper.toDTO(savedRoom)).thenReturn(savedRoomDTO);

        RoomDTO createdRoomDTO = roomService.createRoom(roomDTO);

        assertThat(createdRoomDTO.id()).isNotNull();
        assertThat(createdRoomDTO.id()).isEqualTo(1L);
    }

    @Test
    public void shouldGetRoom() {
        Long id = 1L;

        Room room = new Room();
        room.setId(id);
        room.setCapacity(10);

        RoomDTO roomDTO = new RoomDTO(1L, "T204",60,"Lab","T");

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomDTOMapper.toDTO(room)).thenReturn(roomDTO);

        RoomDTO createdRoomDTO = roomService.getRoom(id);

        assertThat(createdRoomDTO).isNotNull();
        assertThat(createdRoomDTO.id()).isEqualTo(roomDTO.id());
    }

    @Test
    public void shouldGetAllRooms() {
        Room room1 = new Room();
        room1.setId(1L);

        RoomDTO roomDTO1 = new RoomDTO(1L, "T204",60,"Lab","T");

        Room room2 = new Room();
        room2.setId(2L);

        RoomDTO roomDTO2 = new RoomDTO(2L, "T205",60,"Lab","T");

        Pageable pageable = PageRequest.of(0, 50);
        Page<Room> roomPage = new PageImpl<>(List.of(room1, room2), pageable, 2);

        when(roomRepository.findAll(pageable)).thenReturn(roomPage);
        when(roomDTOMapper.toDTO(room1)).thenReturn(roomDTO1);
        when(roomDTOMapper.toDTO(room2)).thenReturn(roomDTO2);

        Page<RoomDTO> response = roomService.getRooms(pageable);

        assertThat(response).isNotNull();
        assertThat(response.getContent().size()).isEqualTo(2);
        assertThat(response.getContent().get(0).id()).isEqualTo(1L);
        assertThat(response.getContent().get(1).id()).isEqualTo(2L);
    }

    @Test
    public void shouldThrowExceptionWhenRoomNotFound() {
        Long id = 99L;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoom(id));
    }

    @Test
    public void shouldThrowExceptionWhenCreateRoomAlreadyExists() {
        RoomDTO roomDTO = new RoomDTO(1L, "T204", 60, "Lab", "T");
        when(roomRepository.existsByNameOrLocation(roomDTO.name(), roomDTO.location())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> roomService.createRoom(roomDTO));
    }

    @Test
    public void shouldUpdateRoom() {
        RoomDTO roomDTO = new RoomDTO(1L, "T205", 80, "Seminar", "T");

        Room existingRoom = new Room();
        existingRoom.setId(1L);
        existingRoom.setName("T204");
        existingRoom.setCapacity(60);
        existingRoom.setType("Lab");
        existingRoom.setLocation("T");

        when(roomRepository.existsByNameOrLocationExcludingId(roomDTO.name(), roomDTO.location(), roomDTO.id())).thenReturn(false);
        when(roomRepository.findById(roomDTO.id())).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(existingRoom)).thenReturn(existingRoom);

        roomService.updateRoom(roomDTO);

        verify(roomRepository).save(existingRoom);
        assertThat(existingRoom.getName()).isEqualTo("T205");
        assertThat(existingRoom.getCapacity()).isEqualTo(80);
        assertThat(existingRoom.getType()).isEqualTo("Seminar");
    }

    @Test
    public void shouldThrowExceptionWhenUpdateRoomNotFound() {
        RoomDTO roomDTO = new RoomDTO(99L, "T204", 60, "Lab", "T");
        when(roomRepository.existsByNameOrLocationExcludingId(roomDTO.name(), roomDTO.location(), roomDTO.id())).thenReturn(false);
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.updateRoom(roomDTO));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateRoomAlreadyExists() {
        RoomDTO roomDTO = new RoomDTO(1L, "duplicate", 60, "Lab", "T");
        when(roomRepository.existsByNameOrLocationExcludingId("duplicate", "T", 1L)).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> roomService.updateRoom(roomDTO));
    }

    @Test
    public void shouldDeleteRoom() {
        Long id = 1L;
        Room room = new Room();
        room.setId(id);
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        roomService.deleteRoom(id);

        verify(roomRepository).deleteById(id);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteRoomNotFound() {
        Long id = 99L;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roomService.deleteRoom(id));
    }
}