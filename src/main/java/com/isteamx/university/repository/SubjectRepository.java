package com.isteamx.university.repository;

import com.isteamx.university.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
     Boolean existsByNameAndActivityType(String name, String activityType);
     Boolean existsByNameAndActivityTypeAndIdNot(String name, String activityType, Long id);
}
