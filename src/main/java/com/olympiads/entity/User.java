package com.olympiads.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.olympiads.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String patronymic;

    @Column(length = 3000)
    private String password;

    private String school;

    @Column(nullable = false, unique = true)
    private String email;

    private String studyClass;

    private String region;

    private String city;

    private String phoneNumber;

    private Timestamp birthdate;

    @Column(name = "role")
    private Role role;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<OlympiadForCalendar> olympiads = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            mappedBy = "creator",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Olympiad> createdByMe = new ArrayList<>();
}
