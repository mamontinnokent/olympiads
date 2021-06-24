package com.example.olympiads.service;

import com.example.olympiads.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {

    User register(User user);
    List<User> getAll();
    User findByEmail(String email);
    User findById(UUID id);
    void delete(UUID id);

}
