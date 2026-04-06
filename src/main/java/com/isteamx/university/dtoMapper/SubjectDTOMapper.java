package com.isteamx.university.dtoMapper;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectDTOMapper {

    public Subject toEntity(SubjectDTO dto) {
        if (dto == null) {
            return null;
        }

        Subject subject = new Subject();
        subject.setId(dto.id());
        subject.setName(dto.name());
        subject.setActivityType(dto.activityType());
        return subject;
    }

    public SubjectDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }

        return new SubjectDTO(
                subject.getId(),
                subject.getName(),
                subject.getActivityType()
        );
    }
}