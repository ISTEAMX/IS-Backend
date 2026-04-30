package com.isteamx.university.controller;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule Management", description = "Endpoints for managing university schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Get schedule by user ID", description = "Returns the schedule for a specific user.")
    @ApiResponse(responseCode = "200", description = "Schedule retrieved successfully")
    @GetMapping("/user/{id}")
    public ScheduleDTO getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    @Operation(summary = "Get all schedules", description = "Returns a list of all schedules in the system.")
    @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully")
    @GetMapping("/user")
    public List<ScheduleDTO> getSchedules(){
        return scheduleService.getSchedules();
    }

    @Operation(summary = "Add a new schedule entry", description = "Creates a new schedule entry.")
    @ApiResponse(responseCode = "200", description = "Schedule added successfully")
    @PostMapping("/add")
    public ScheduleDTO addSchedule(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
            return scheduleService.addSchedule(createScheduleRequestDTO);
    }

    @Operation(summary = "Update an existing schedule", description = "Updates the details of a schedule entry.")
    @ApiResponse(responseCode = "200", description = "Schedule updated successfully")
    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        scheduleService.updateSchedule(createScheduleRequestDTO);
        return ResponseEntity.ok("Schedule updated");
    }

    @Operation(summary = "Delete a schedule entry", description = "Removes a schedule entry by its ID.")
    @ApiResponse(responseCode = "200", description = "Schedule deleted successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("Schedule deleted");
    }

    @Operation(summary = "Filter schedules", description = "Returns schedules matching the provided filters.")
    @ApiResponse(responseCode = "200", description = "Filtered schedules retrieved successfully")
    @GetMapping("/user/filter")
    public List<ScheduleDTO> getSchedulesByFilters(@ModelAttribute FilterDTO filterDTO) {
        return scheduleService.getSchedulesByFilters(filterDTO);
    }

}
