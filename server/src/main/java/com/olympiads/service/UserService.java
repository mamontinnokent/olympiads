package com.olympiads.service;


import com.olympiads.entity.User;
import com.olympiads.exceptions.UserExistExceptiom;
import com.olympiads.payload.request.SignupRequest;
import com.olympiads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();

        String[] birthDate = userIn.getBirthdate().split("/");

        user.setEmail       (userIn.getEmail());
        user.setName        (userIn.getName());
        user.setSurname     (userIn.getSurname());
        user.setPatronymic  (userIn.getPatronymic());
        user.setStudyClass  (userIn.getStudyClass());
        user.setSchool      (userIn.getSchool());
        user.setPlaceOfLife (userIn.getPlaceOfLife());
        user.setBirthdate   (LocalDate.of(Integer.parseInt(birthDate[2]), Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0])));
        user.setLessons     (userIn.getLessons());
        user.setPassword    (bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add (userIn.getRole());

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistExceptiom("The user " + user.getEmail() + " already exist. Please check credentials");
        }
    }
}
