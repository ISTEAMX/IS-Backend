package com.isteamx.university.service.impl;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.dtoMapper.ProfessorDTOMapper;
import com.isteamx.university.entity.Professor;
import com.isteamx.university.exception.ResourceNotFoundException;
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
        Professor professor = professorRepository.findById(professorId).orElseThrow(()->new ResourceNotFoundException("Professor Not Found"));
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

        Professor professor = professorRepository.findById(professorDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Professor with ID " + professorDTO.id() + " does not exist"));

        professor.setFirstName(professorDTO.firstName());
        professor.setLastName(professorDTO.lastName());
        professor.setDepartment(professorDTO.department());

        Professor savedProfessor = professorRepository.save(professor);

        professorDTOMapper.toDTO(savedProfessor);

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProfessor(Long professorId) {
        if(professorRepository.findById(professorId).isEmpty()){
            throw new ResourceNotFoundException("Professor with id " + professorId + " doesn't exist");
        }
        professorRepository.deleteById(professorId);
    }


}
