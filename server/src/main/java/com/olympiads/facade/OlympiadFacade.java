package com.olympiads.facade;

import com.olympiads.dto.OlympiadDTO;
import com.olympiads.entity.Olympiad;
import org.springframework.stereotype.Component;

@Component
public class OlympiadFacade {

    public OlympiadDTO olympiadToOlympiadDTO(Olympiad olympiad) {
        return OlympiadDTO.builder()
                .id(olympiad.getId())
                .lesson(olympiad.getLesson())
                .location(olympiad.getLocation())
                .title(olympiad.getTitle())
                .build();
    }
}
