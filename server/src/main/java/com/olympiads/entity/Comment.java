package com.olympiads.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "olympiad_id")
    private Olympiad olympiad;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(nullable = false)
    private Timestamp createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
