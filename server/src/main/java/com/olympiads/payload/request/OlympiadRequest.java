package com.olympiads.payload.request;

import com.olympiads.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OlympiadRequest {

    private Long id;
    private String title;
    private String caption;
    private String location;
    private String lesson;
    private String link;
    private User creator;
    private LocalDate dateOfOlympiads;

}
