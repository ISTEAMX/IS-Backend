package com.isteamx.university.repository;

import com.isteamx.university.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByNameOrLocation(String name,String location);
    boolean existsByNameOrLocationAndIdNot(String name,String location, Long id);

}
