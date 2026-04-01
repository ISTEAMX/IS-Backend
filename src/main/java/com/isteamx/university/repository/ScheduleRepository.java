package com.isteamx.university.repository;

import com.isteamx.university.entity.Room;
import com.isteamx.university.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoomAndScheduleDayAndStartingHour(Room room, String schedule, String startingHour);
}
