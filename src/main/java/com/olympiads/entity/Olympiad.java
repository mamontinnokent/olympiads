package com.olympiads.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Olympiad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String caption;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String lesson;

    @Column(nullable = false)
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator;

    private Timestamp dateOfOlympiad;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH,
            mappedBy = "olympiad"
    )
    private List<Comment> comments;
}
