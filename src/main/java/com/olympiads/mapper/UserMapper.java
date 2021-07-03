package com.olympiads.mapper;

import com.olympiads.dto.all.UserDTO;
import com.olympiads.entity.User;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPatronymic(user.getPatronymic());
        dto.setEmail(user.getEmail());
        dto.setStudyClass(user.getStudyClass());
        dto.setSchool(user.getSchool());
        dto.setRegion(user.getRegion());
        dto.setCity(user.getCity());
        dto.setRole(user.getRole());
        dto.setBirthdate(Utility.timestampToString(user.getBirthdate()));

        return dto;
    }

    public User update(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setSchool(userDTO.getSchool());
        user.setStudyClass(userDTO.getStudyClass());
        user.setBirthdate(Utility.stringToTimestamp(userDTO.getBirthdate()));
        user.setRegion(userDTO.getRegion());
        user.setCity(userDTO.getCity());

        return user;
    }

}
