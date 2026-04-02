package com.isteamx.university.service.impl;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.dtoMapper.SubjectDTOMapper;
import com.isteamx.university.entity.Subject;
import com.isteamx.university.exception.AlreadyExistsException;
import com.isteamx.university.exception.ResourceNotFoundException;
import com.isteamx.university.repository.SubjectRepository;
import com.isteamx.university.service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectDTOMapper subjectDTOMapper;

    @Override
    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return subjectDTOMapper.toDTO(subject);
    }

    @Override
    public List<SubjectDTO> getSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(subjectDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        if(subjectRepository.existsByNameAndActivityType(subjectDTO.getName(),subjectDTO.getActivityType())){
            throw new AlreadyExistsException("Subject already exists");
        }

        Subject subject = subjectDTOMapper.toEntity(subjectDTO);

       Subject savedSubject =  subjectRepository.save(subject);

        return subjectDTOMapper.toDTO(savedSubject);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateSubject(SubjectDTO subjectDTO) {

        Subject subject = subjectRepository.findById(subjectDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        if(subjectRepository.existsByNameAndActivityTypeAndIdNot(subjectDTO.getName(),subjectDTO.getActivityType(),subjectDTO.getId())){
            throw new AlreadyExistsException("Subject already exists");
        }

        subject.setName(subjectDTO.getName());
        subject.setActivityType(subjectDTO.getActivityType());
        subjectRepository.save(subject);

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSubject(Long id) {
        if(subjectRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Subject not found");
        }
        subjectRepository.deleteById(id);
    }
}
