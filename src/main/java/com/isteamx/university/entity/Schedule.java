package com.isteamx.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String scheduleDay;

    @Column(nullable = false)
    private String startingHour;

    @Column(nullable = false)
    private String endingHour;

    @JoinColumn(name = "professor_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Professor professor;

    @JoinColumn(name = "room_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Room room;

    @JoinColumn(name = "group_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Group group;

    @JoinColumn(name = "subject_id",referencedColumnName = "id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Subject subject;


}
