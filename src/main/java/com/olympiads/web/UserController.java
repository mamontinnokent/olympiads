package com.olympiads.web;

import com.olympiads.dto.all.UserDTO;
import com.olympiads.service.UserService;
import com.olympiads.validation.ResponseErrorValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrent(Principal principal) {
        UserDTO userDTO = userService.getCurrentDTO(principal);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable String id) {
        return new ResponseEntity<>(userService.getDTO(Long.parseLong(id)), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        UserDTO userUpdated = userService.update(userDTO, principal);

        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
