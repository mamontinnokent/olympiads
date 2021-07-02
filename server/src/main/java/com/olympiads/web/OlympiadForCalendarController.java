package com.olympiads.web;


import com.olympiads.dto.CommentDTO;
import com.olympiads.dto.OlympiadForCalendarDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.facade.OlympiadForCalendarFacade;
import com.olympiads.facade.UserFacade;
import com.olympiads.payload.response.MessageResponse;
import com.olympiads.service.OlympiadForCalendarService;
import com.olympiads.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/ofc")
public class OlympiadForCalendarController {

    @Autowired
    private OlympiadForCalendarService olympiadForCalendarService;
    @Autowired
    private OlympiadForCalendarFacade olympiadForCalendarFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/add")
    public ResponseEntity<Object> addOfcToUser(@Valid @RequestBody OlympiadForCalendarDTO olympiadForCalendarDTO, Principal principal, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        olympiadForCalendarService.addToUser(olympiadForCalendarDTO, principal);

        return ResponseEntity.ok(new MessageResponse("Olympiad was added to calendar."));
    }

    @GetMapping("/")
    public ResponseEntity<List<OlympiadForCalendarDTO>> addOfcToUser(Principal principal) {
        List<OlympiadForCalendarDTO> olympiads = olympiadForCalendarService.allForUser(principal).stream()
                .map(olympiadForCalendarFacade::ofcToOfcDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(olympiads);
    }


}
