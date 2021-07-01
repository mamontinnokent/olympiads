package com.olympiads.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OlympiadDTO {

    private Long id;
    private String title;
    private String location;
    private String lesson;
    private String caption;

}
