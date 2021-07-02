package com.olympiads.facade;

import com.olympiads.dto.UserDTO;
import com.olympiads.entity.User;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class UserFacade {

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

}
