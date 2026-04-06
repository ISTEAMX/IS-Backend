package com.isteamx.university.service;

import com.isteamx.university.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    SubjectDTO getSubjectById(Long id);
    List<SubjectDTO> getSubjects();
    SubjectDTO createSubject(SubjectDTO subjectDTO);
    void updateSubject(SubjectDTO subjectDTO);
    void deleteSubject(Long id);
}
