package com.olympiads.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String school;
    private String studyClass;
    private String placeOfLife;
    private String birthdate;
    private String lessons;

}
