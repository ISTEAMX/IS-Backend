package com.isteamx.university.service;

import com.isteamx.university.dto.ProfessorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfessorService {
    ProfessorDTO getProfessor(Long professorId);
    List<ProfessorDTO> getProfessors();
    void updateProfessor(ProfessorDTO professor);
    void deleteProfessor(Long professorId);
}
