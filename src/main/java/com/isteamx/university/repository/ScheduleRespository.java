package com.isteamx.university.repository;

import com.isteamx.university.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRespository extends JpaRepository<Schedule, Long> {
}
