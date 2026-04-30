package com.isteamx.university.dto;

import com.isteamx.university.entity.Schedule;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Room name is required")
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotBlank(message = "Room type is required")
    private String type;

    @NotBlank(message = "Location is required")
    private String location;

    private List<String> equipment;
    private List<Schedule> schedule;
}
