package com.isteamx.university.controller;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public ScheduleDTO addSchedule(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
            return scheduleService.addSchedule(createScheduleRequestDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        scheduleService.updateSchedule(createScheduleRequestDTO);
        return ResponseEntity.ok("Schedule updated");
    }

}
