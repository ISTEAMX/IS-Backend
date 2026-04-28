package com.isteamx.university.service;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {

    ScheduleDTO getSchedule(Long id);

    List<ScheduleDTO> getSchedules();

    ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO);

    void updateSchedule(CreateScheduleRequestDTO createScheduleRequestDTO);

    void deleteSchedule(Long id);

    List<ScheduleDTO> getSchedulesByFilters(FilterDTO filterDTO);

    void approveSchedule(Long id);

}
