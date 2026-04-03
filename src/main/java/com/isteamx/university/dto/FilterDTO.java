package com.isteamx.university.dto;

import com.isteamx.university.enums.Frequency;

public record FilterDTO(Long professorId, Long roomId, Long groupId, Long subjectId, String scheduleDay, Frequency frequency) {}
