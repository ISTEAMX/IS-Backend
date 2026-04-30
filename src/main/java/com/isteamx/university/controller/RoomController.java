package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.RoomDTO;
import com.isteamx.university.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Tag(name = "Room Management", description = "Endpoints for managing university classrooms and facilities")
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Get room by ID", description = "Returns details of a specific classroom.")
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseWrapper<RoomDTO>> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(roomService.getRoom(id), "Room retrieved successfully"));
    }

    @Operation(summary = "Get all rooms", description = "Returns a paginated list of all available classrooms.")
    @GetMapping("/user/rooms")
    public ResponseEntity<ApiResponseWrapper<Page<RoomDTO>>> getRooms(Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(roomService.getRooms(pageable), "Rooms retrieved successfully"));
    }

    @Operation(summary = "Create a new room", description = "Registers a new classroom in the system.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<RoomDTO>> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.created(roomService.createRoom(roomDTO), "Room created successfully"));
    }

    @Operation(summary = "Update room details", description = "Updates capacity, type, or name of a room.")
    @PutMapping("/update")
    public ResponseEntity<ApiResponseWrapper<Void>> updateRoom(@Valid @RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Room updated successfully"));
    }

    @Operation(summary = "Delete a room", description = "Removes a room from the system by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(ApiResponseWrapper.success("Room deleted successfully"));
    }
}
