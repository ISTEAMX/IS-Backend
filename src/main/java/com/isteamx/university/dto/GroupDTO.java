package com.isteamx.university.dto;
import com.isteamx.university.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String identifier;
    private String specialization;
    private int year;
    List<Schedule> schedules;
}
