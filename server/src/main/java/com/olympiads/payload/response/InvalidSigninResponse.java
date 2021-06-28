package com.olympiads.payload.response;

import lombok.Getter;

@Getter
public class InvalidSigninResponse {

    private String email = "Invalid Email";
    private String password = "Invalid password";

}
