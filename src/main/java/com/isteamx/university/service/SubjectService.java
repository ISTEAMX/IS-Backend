package com.isteamx.university.service;

import com.isteamx.university.dto.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {

    SubjectDTO getSubjectById(Long id);
    Page<SubjectDTO> getSubjects(Pageable pageable);
    SubjectDTO createSubject(SubjectDTO subjectDTO);
    void updateSubject(SubjectDTO subjectDTO);
    void deleteSubject(Long id);
}
