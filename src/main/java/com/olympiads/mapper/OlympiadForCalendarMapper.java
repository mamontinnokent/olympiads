package com.olympiads.mapper;

import com.olympiads.dto.all.OlympiadForCalendarDTO;
import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.entity.User;
import com.olympiads.utility.Utility;
import org.springframework.stereotype.Component;

@Component
public class OlympiadForCalendarMapper {
    public OlympiadForCalendarDTO ofcToOfcDTO(OlympiadForCalendar olympiad) {
        return OlympiadForCalendarDTO.builder()
                .id(olympiad.getId())
                .olympiadId(olympiad.getOlympiadId())
                .olympiadTitle(olympiad.getOlympiadName())
                .dateOlympiad(Utility.timestampToString(olympiad.getDateOlympiad()))
                .build();
    }
}
