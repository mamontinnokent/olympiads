package com.olympiads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistExceptiom extends RuntimeException {
    public UserExistExceptiom(String message) {
        super(message);
    }
}
