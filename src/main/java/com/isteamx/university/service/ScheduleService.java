package com.isteamx.university.service;

import com.isteamx.university.dto.CreateScheduleRequestDTO;
import com.isteamx.university.dto.FilterDTO;
import com.isteamx.university.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ScheduleDTO getSchedule(Long id);

    Page<ScheduleDTO> getSchedules(Pageable pageable);

    ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO);

    void updateSchedule(CreateScheduleRequestDTO createScheduleRequestDTO);

    void deleteSchedule(Long id);

    Page<ScheduleDTO> getSchedulesByFilters(FilterDTO filterDTO, Pageable pageable);
}
