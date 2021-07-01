package com.olympiads.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.olympiads.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private String placeOfLife;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private LocalDate birthdate;

    @Column(length = 500)
    private String lessons;

    @Column(nullable = false, name = "role")
    private Role role;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<OlympiadForCalendar> olympiads = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            mappedBy = "creator",
            orphanRemoval = true
    )
    private List<Olympiad> createdByMe = new ArrayList<>();
}
