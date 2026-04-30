package com.isteamx.university.controller;

import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/user/{id}")
    public RoomDTO getRoom(@PathVariable Long id) {
        return roomService.getRoom(id);
    }

    @GetMapping("/user/rooms")
    public List<RoomDTO> getRooms() {
        return roomService.getRooms();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        roomService.createRoom(roomDTO);
        return ResponseEntity.ok(Map.of("message", "Room created"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> updateRoom(@RequestBody @Valid RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
        return ResponseEntity.ok(Map.of("message", "Room updated"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(Map.of("message", "Room deleted"));
    }
}
