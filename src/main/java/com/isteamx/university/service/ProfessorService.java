package com.isteamx.university.service;

import com.isteamx.university.dto.ProfessorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfessorService {
    ProfessorDTO getProfessor(Long professorId);
    Page<ProfessorDTO> getProfessors(Pageable pageable);
    void updateProfessor(ProfessorDTO professor);
    void deleteProfessor(Long professorId);
}
