package com.olympiads.dto.all;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {

    private Long id;
    private String name;
    private String surname;
    private String message;
    private String dateCreated;

}
