package com.isteamx.university.service.impl;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.repository.ProfessorRepository;
import com.isteamx.university.service.ProfessorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorDTOMapper professorDTOMapper;

    @Override
    public ProfessorDTO getProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(()->new RuntimeException("Professor Not Found"));
        return professorDTOMapper.toDTO(professor);
    }

    @Override
    public List<ProfessorDTO> getProfessors() {
        List<Professor> professors = professorRepository.findAll();
        return professors.stream().map(professorDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateProfessor(ProfessorDTO professorDTO) {

        Professor professor = professorRepository.findById(professorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Professor with ID " + professorDTO.getId() + " does not exist"));

        professor.setFirstName(professorDTO.getFirstName());
        professor.setLastName(professorDTO.getLastName());
        professor.setDepartment(professorDTO.getDepartment());

        Professor savedProfessor = professorRepository.save(professor);

        professorDTOMapper.toDTO(savedProfessor);

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor with ID " + professorId + " does not exist"));

        professorRepository.delete(professor);
    }


}
