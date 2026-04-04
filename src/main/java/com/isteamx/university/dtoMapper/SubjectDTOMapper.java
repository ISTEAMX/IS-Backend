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
            subject.setId(d.id());
            subject.setName(d.name());
            subject.setActivityType(d.activityType());

            return subject;
        }).orElse(null);
    }

    public SubjectDTO toDTO(Subject subject){
        return Optional.ofNullable(subject).map(e ->
                new SubjectDTO(e.getId(),e.getName(),e.getActivityType())
        ).orElse(null);
    }

}
