package com.isteamx.university.service;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.ScheduleDTO;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {

    ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO);

}
