package com.isteamx.university.repository;

import com.isteamx.university.entity.Group;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.Room;
import com.isteamx.university.entity.Schedule;
import com.isteamx.university.enums.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoomAndScheduleDayAndStartingHour(Room room, String schedule, String startingHour);

    Boolean existsByGroupAndStartingHourAndScheduleDayAndFrequency(Group group, String startingHour ,String scheduleDay, Frequency frequency);

    Boolean existsByProfessorAndStartingHourAndScheduleDayAndFrequency(Professor professor, String startingHour, String scheduleDay, Frequency frequency);
}
