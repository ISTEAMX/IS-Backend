package com.isteamx.university.controller;
import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<String> createRoom(@RequestBody RoomDTO roomDTO) {
                roomService.createRoom(roomDTO);
        return ResponseEntity.ok("Room created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRoom(@RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
        return ResponseEntity.ok("Room updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted");
    }
}
