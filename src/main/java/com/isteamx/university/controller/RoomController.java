package com.isteamx.university.controller;
import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Tag(name = "Room Management", description = "Endpoints for managing university classrooms and facilities")
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Get room by ID", description = "Returns details of a specific classroom.")
    @ApiResponse(responseCode = "200", description = "Room retrieved successfully")
    @GetMapping("/user/{id}")
    public RoomDTO getRoom(@PathVariable Long id) {
        return roomService.getRoom(id);
    }

    @Operation(summary = "Get all rooms", description = "Returns a list of all available classrooms.")
    @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    @GetMapping("/user/rooms")
    public List<RoomDTO> getRooms() {
        return roomService.getRooms();
    }

    @Operation(summary = "Create a new room", description = "Registers a new classroom in the system.")
    @ApiResponse(responseCode = "200", description = "Room created successfully")
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody RoomDTO roomDTO) {
                roomService.createRoom(roomDTO);
        return ResponseEntity.ok("Room created");
    }

    @Operation(summary = "Update room details", description = "Updates capacity, type, or name of a room.")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
    @PutMapping("/update")
    public ResponseEntity<String> updateRoom(@RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
        return ResponseEntity.ok("Room updated");
    }

    @Operation(summary = "Delete a room", description = "Removes a room from the system by its ID.")
    @ApiResponse(responseCode = "200", description = "Room deleted successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted");
    }
}
