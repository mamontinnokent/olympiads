package com.olympiads.mapper;

import com.olympiads.dto.all.OlympiadDTO;
import com.olympiads.dto.request.OlympiadRequest;
import com.olympiads.entity.Olympiad;
import com.olympiads.entity.User;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

@Component
public class OlympiadMapper {

    public OlympiadDTO olympiadToOlympiadDTO(Olympiad olympiad) {
        return OlympiadDTO.builder()
                .id(olympiad.getId())
                .lesson(olympiad.getLesson())
                .location(olympiad.getLocation())
                .title(olympiad.getTitle())
                .caption(olympiad.getCaption())
                .link(olympiad.getLink())
                .date(Utility.timestampToString(olympiad.getDateOfOlympiad()))
                .build();
    }

    public Olympiad olympiadRequestToOlympiad(OlympiadRequest olympiadRequest, User user) {
        Olympiad olympiad = new Olympiad();

        olympiad.setTitle(olympiadRequest.getTitle());
        olympiad.setCaption(olympiadRequest.getCaption());
        olympiad.setLocation(olympiadRequest.getLocation());
        olympiad.setLesson(olympiadRequest.getLesson());
        olympiad.setLink(olympiadRequest.getLink());
        olympiad.setCreator(user);
        olympiad.setDateOfOlympiad(Utility.stringToTimestamp(olympiadRequest.getDateOfOlympiads()));
        user.getCreatedByMe().add(olympiad);

        return  olympiad;
    }

    public Olympiad olympiadDTOToOlympiad(OlympiadDTO olympiadDTO) {
        Olympiad olympiad = new Olympiad();

        olympiad.setTitle(olympiadDTO.getTitle());
        olympiad.setCaption(olympiadDTO.getCaption());
        olympiad.setLocation(olympiadDTO.getLocation());
        olympiad.setLesson(olympiadDTO.getLesson());
        olympiad.setLink(olympiadDTO.getLink());
        olympiad.setDateOfOlympiad(Utility.stringToTimestamp(olympiadDTO.getDate()));

        return olympiad;
    }
}
