package com.isteamx.university.repository;

import com.isteamx.university.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
