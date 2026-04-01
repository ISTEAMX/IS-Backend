package com.isteamx.university.repository;

import com.isteamx.university.entity.Room;
import com.isteamx.university.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Boolean existsByRoomAndScheduleDayAndStartingHour(Room room, String schedule,String startingHour);
}
