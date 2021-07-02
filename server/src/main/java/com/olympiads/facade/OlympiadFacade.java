package com.olympiads.facade;

import com.olympiads.dto.OlympiadDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OlympiadFacade {

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
}
