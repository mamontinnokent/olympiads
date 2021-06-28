package com.olympiads.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
public class Olympiad {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String title;
    private String caption;
    private String location;
    private String lesson;
    private String link;

    private User creator;
    private List<Comment> comments;
}
