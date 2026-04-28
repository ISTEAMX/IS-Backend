package com.isteamx.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int semester;

    @ManyToMany(mappedBy = "groups")
    private List<Schedule> schedule;
}
