package com.olympiads.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SigninRequest {

    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password cannot be empty")
    private String password;

}
