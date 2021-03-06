package com.olympiads.dto.all;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OlympiadForCalendarDTO {

    private Long id;
    private Long olympiadId;
    private String olympiadTitle;
    private String dateOlympiad;

}
