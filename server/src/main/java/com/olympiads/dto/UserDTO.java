package com.olympiads.dto;

import com.olympiads.entity.enums.Role;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String school;
    private String studyClass;
    private String region;
    private String city;
    private String birthdate;
    private Role role;

}
