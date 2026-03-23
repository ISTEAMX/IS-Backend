package com.isteamx.university.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "student_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String identifier;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<Schedule> schedule;
}
