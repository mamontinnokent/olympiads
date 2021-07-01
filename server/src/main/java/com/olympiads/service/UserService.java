package com.olympiads.service;


import com.olympiads.dto.UserDTO;
import com.olympiads.entity.User;
import com.olympiads.exceptions.UserExistException;
import com.olympiads.payload.request.SignupRequest;
import com.olympiads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

        user.setEmail(userIn.getEmail());
        user.setName(userIn.getName());
        user.setSurname(userIn.getSurname());
        user.setPatronymic(userIn.getPatronymic());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.setRole(userIn.getRole());

        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.saveAndFlush(user);
        } catch (Exception e) {
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getEmail() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));

        String[] birthDate = userDTO.getBirthdate().split("/");
        LocalDate newBirthDate = LocalDate.of(Integer.parseInt(birthDate[2]), Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0]));

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setSchool(userDTO.getSchool());
        user.setStudyClass(userDTO.getStudyClass());
        user.setPlaceOfLife(userDTO.getPlaceOfLife());
        user.setBirthdate(newBirthDate);
        user.setLessons(userDTO.getLessons());

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with id " + id));
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }
}
