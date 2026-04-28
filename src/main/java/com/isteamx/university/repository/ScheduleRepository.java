package com.isteamx.university.repository;

import com.isteamx.university.entity.Group;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.entity.Room;
import com.isteamx.university.entity.Schedule;
import com.isteamx.university.enums.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT DISTINCT s FROM Schedule s LEFT JOIN s.groups g WHERE " +
            "(:professorId IS NULL OR s.professor.id = :professorId) AND " +
            "(:roomId IS NULL OR s.room.id = :roomId) AND " +
            "(:groupId IS NULL OR g.id = :groupId) AND " +
            "(:subjectId IS NULL OR s.subject.id = :subjectId) AND " +
            "(:scheduleDay IS NULL OR s.scheduleDay = :scheduleDay) AND " +
            "(:frequency IS NULL OR s.frequency = :frequency)")

    List<Schedule> findSchedulesByDynamicFilters(
            @Param("professorId") Long professorId,
            @Param("roomId") Long roomId,
            @Param("groupId") Long groupId,
            @Param("subjectId") Long subjectId,
            @Param("scheduleDay") String scheduleDay,
            @Param("frequency") Frequency frequency
    );

    List<Schedule> findByRoomAndScheduleDayAndStartingHour(Room room, String schedule, String startingHour);

    Boolean existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequency(Group group, String startingHour, String scheduleDay, Frequency frequency);

    Boolean existsByProfessorAndStartingHourAndScheduleDayAndFrequency(Professor professor, String startingHour, String scheduleDay, Frequency frequency);
    
    Boolean existsByGroupsContainingAndStartingHourAndScheduleDayAndFrequencyAndIdNot(Group group, String startingHour, String scheduleDay, Frequency frequency, Long id);

    Boolean existsByProfessorAndStartingHourAndScheduleDayAndFrequencyAndIdNot(Professor professor, String startingHour, String scheduleDay, Frequency frequency, Long id);
}
