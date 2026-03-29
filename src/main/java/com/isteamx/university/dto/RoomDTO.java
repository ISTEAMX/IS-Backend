package com.isteamx.university.dto;

import com.isteamx.university.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String name;
    private int capacity;
    private String type;
    private String location;
    private List<String> equipment;
    private List<Schedule> schedule;
}
