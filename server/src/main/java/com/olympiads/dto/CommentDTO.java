package com.olympiads.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CommentDTO {

    private Long id;
    private String name;
    private String surname;
    private String message;
    private String dateCreated;

}
