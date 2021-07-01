package com.olympiads.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
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
    private User creator;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REFRESH,
            mappedBy = "olympiad"
    )
    private List<Comment> comments;

    private LocalDate dateOfOlympiads;
}
