package com.isteamx.university.controller;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.ScheduleDTO;
import com.isteamx.university.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add")
    public ScheduleDTO addSchedule(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
            return scheduleService.addSchedule(createScheduleRequestDTO);
    }

}
