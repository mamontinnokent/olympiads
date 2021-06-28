package com.olympiads.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.olympiads.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private String patronymic;

    @Column(length = 3000)
    private String password;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String studyClass;

    @Column(nullable = false)
    private String placeOfLife;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private LocalDate birthdate;
    @Column(length = 500)
    private String lessons;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(columnDefinition = "user_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;
}
