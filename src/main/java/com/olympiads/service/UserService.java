package com.olympiads.service;


import com.olympiads.dto.all.UserDTO;
import com.olympiads.entity.User;
import com.olympiads.exceptions.UserExistException;
import com.olympiads.dto.request.SignupRequest;
import com.olympiads.mapper.UserMapper;
import com.olympiads.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public UserDTO update(UserDTO userDTO, Principal principal) {
        String email = principal.getName();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));

        user = userMapper.update(userDTO, user);
        user = userRepository.save(user);

        return userMapper.userToUserDTO(user);
    }

    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with id " + id));
    }

    public UserDTO getDTO(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with id " + id));
        return userMapper.userToUserDTO(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }


    public UserDTO getCurrentDTO(Principal principal) {
        return userMapper.userToUserDTO(getCurrent(principal));
    }

    private User getCurrent(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }
}
