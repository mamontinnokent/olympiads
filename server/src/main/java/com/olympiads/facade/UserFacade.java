package com.olympiads.facade;

import com.olympiads.dto.UserDTO;
import com.olympiads.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .school(user.getSchool())
                .studyClass(user.getStudyClass())
                .placeOfLife(user.getPlaceOfLife())
                .birthdate(user.getBirthdate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
                .lessons(user.getLessons())
                .build();
    }

}
