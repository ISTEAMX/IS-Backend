package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubjectDTOMapper {

    public Subject toEntity(SubjectDTO subjectDTO){
        return Optional.ofNullable(subjectDTO).map(d -> {
            Subject subject = new Subject();
            subject.setId(d.getId());
            subject.setName(d.getName());
            subject.setActivityType(d.getActivityType());

            return subject;
        }).orElse(null);
    }

    public SubjectDTO toDTO(Subject subject){
        return Optional.ofNullable(subject).map(e -> {
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setId(e.getId());
            subjectDTO.setName(e.getName());
            subjectDTO.setActivityType(e.getActivityType());


            return subjectDTO;
        }).orElse(null);
    }

}
