package com.olympiads.facade;

import com.olympiads.dto.OlympiadForCalendarDTO;
import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

@Component
public class OlympiadForCalendarFacade {
    public OlympiadForCalendarDTO ofcToOfcDTO(OlympiadForCalendar olympiad) {
        return OlympiadForCalendarDTO.builder()
                .id(olympiad.getId())
                .olympiadId(olympiad.getOlympiadId())
                .olympiadTitle(olympiad.getOlympiadName())
                .dateOlympiad(Utility.timestampToString(olympiad.getDateOlympiad()))
                .build();
    }
}
