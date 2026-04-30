package com.isteamx.university.service;

import com.isteamx.university.dto.ProfessorDTO;

import java.util.List;

public interface ProfessorService {
    ProfessorDTO getProfessor(Long professorId);
    List<ProfessorDTO> getProfessors();
    void updateProfessor(ProfessorDTO professor);
    void deleteProfessor(Long professorId);
}
