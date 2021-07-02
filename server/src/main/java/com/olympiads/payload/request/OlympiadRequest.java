package com.olympiads.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OlympiadRequest {

    @NotEmpty(message = "Please enter title")
    private String title;
    @NotEmpty(message = "Please enter caption")
    private String caption;
    @NotEmpty(message = "Please enter location")
    private String location;
    @NotEmpty(message = "Please enter lesson")
    private String lesson;
    @NotEmpty(message = "Please enter link")
    private String link;
    @NotEmpty(message = "Please enter date")
    private String dateOfOlympiads;

}
