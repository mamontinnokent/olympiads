package com.olympiads.payload.request;


import com.olympiads.annotation.PasswordMatches;
import com.olympiads.annotation.ValidEmail;
import com.olympiads.entity.enums.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@PasswordMatches
public class SignupRequest {
    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter you name")
    private String name;
    @NotEmpty(message = "Please enter you surname")
    private String surname;
    @NotEmpty(message = "Please enter you patronymic")
    private String patronymic;

    @NotEmpty(message = "Please enter you school")
    private String school;
    @NotEmpty(message = "Please enter you class")
    private String studyClass;
    @NotEmpty(message = "Please enter you town")
    private String placeOfLife;
    @NotEmpty(message = "Please enter you birthdate")
    private String birthdate;
    @NotEmpty(message = "Please enter you lessons")
    private String lessons;
    @NotEmpty(message = "Please enter you lessons")
    private Role role;

    @NotEmpty(message = "Please enter you password")
    private String password;
    private String confirmPassword;

}
