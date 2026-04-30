package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.service.ScheduleService;
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
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule Management", description = "Endpoints for managing university schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Get schedule by user ID", description = "Returns the schedule for a specific user.")
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseWrapper<ScheduleDTO>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(scheduleService.getSchedule(id), "Schedule retrieved successfully"));
    }

    @Operation(summary = "Get all schedules", description = "Returns a paginated list of all schedules in the system.")
    @GetMapping("/user")
    public ResponseEntity<ApiResponseWrapper<Page<ScheduleDTO>>> getSchedules(Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(scheduleService.getSchedules(pageable), "Schedules retrieved successfully"));
    }

    @Operation(summary = "Add a new schedule entry", description = "Creates a new schedule entry.")
    @PostMapping("/add")
    public ResponseEntity<ApiResponseWrapper<ScheduleDTO>> addSchedule(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.created(scheduleService.addSchedule(createScheduleRequestDTO), "Schedule added successfully"));
    }

    @Operation(summary = "Update an existing schedule", description = "Updates the details of a schedule entry.")
    @PutMapping("/update")
    public ResponseEntity<ApiResponseWrapper<Void>> updateSchedule(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        scheduleService.updateSchedule(createScheduleRequestDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Schedule updated successfully"));
    }

    @Operation(summary = "Delete a schedule entry", description = "Removes a schedule entry by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponseWrapper.success("Schedule deleted successfully"));
    }

    @Operation(summary = "Filter schedules", description = "Returns schedules matching the provided filters.")
    @GetMapping("/user/filter")
    public ResponseEntity<ApiResponseWrapper<Page<ScheduleDTO>>> getSchedulesByFilters(@ModelAttribute FilterDTO filterDTO, Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(scheduleService.getSchedulesByFilters(filterDTO, pageable), "Filtered schedules retrieved successfully"));
    }

}
