package com.olympiads.web;

import com.olympiads.dto.UserDTO;
import com.olympiads.entity.User;
import com.olympiads.facade.UserFacade;
import com.olympiads.service.UserService;
import com.olympiads.validation.ResponseErrorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable String id) {
        User user = userService.getUserById(Long.parseLong(id));
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("update/")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @GetMapping("getAllUser/")
    public ResponseEntity<Object> getAllUser() {
        List<UserDTO> users = userService.getAllUser().stream()
                .map(userFacade::userToUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
