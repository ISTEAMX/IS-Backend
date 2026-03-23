package com.isteamx.university.repository;

import com.isteamx.university.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
