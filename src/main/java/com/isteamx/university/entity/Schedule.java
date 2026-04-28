package com.isteamx.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isteamx.university.enums.Frequency;
import com.isteamx.university.enums.Pending;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String scheduleDay;

    @Column(nullable = false)
    private String startingHour;

    @Column(nullable = false)
    private String endingHour;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Pending pending;

    @JoinColumn(name = "professor_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Professor professor;

    @JoinColumn(name = "room_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "schedule_groups",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    @JoinColumn(name = "subject_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Subject subject;


}
