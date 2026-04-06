package com.isteamx.university.repository;

import com.isteamx.university.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByNameOrLocation(String name,String location);

    @Query("SELECT COUNT(r) > 0 FROM Room r WHERE (r.name = :name OR r.location = :location) AND r.id != :id")
    boolean existsByNameOrLocationExcludingId(@Param("name") String name, @Param("location") String location, @Param("id") Long id);

}
