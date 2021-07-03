package com.olympiads.web;


import com.olympiads.dto.all.OlympiadForCalendarDTO;
import com.olympiads.dto.response.MessageResponse;
import com.olympiads.mapper.OlympiadForCalendarMapper;
import com.olympiads.service.OlympiadForCalendarService;
import com.olympiads.validation.ResponseErrorValidation;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/ofc")
public class OlympiadForCalendarController {

    private final ResponseErrorValidation responseErrorValidation;
    private final OlympiadForCalendarMapper olympiadForCalendarMapper;
    private final OlympiadForCalendarService olympiadForCalendarService;

    @PostMapping("/add")
    public ResponseEntity<Object> addOfcToUser(@Valid @RequestBody OlympiadForCalendarDTO olympiadForCalendarDTO, Principal principal, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        boolean isAdded = olympiadForCalendarService.addToUser(olympiadForCalendarDTO, principal);

        if (isAdded) return ResponseEntity.ok(new MessageResponse("Olympiad was added."));
        else return ResponseEntity.ok(new MessageResponse("Olympiad added yet."));
    }

    @GetMapping("/")
    public ResponseEntity<List<OlympiadForCalendarDTO>> viewForUser(Principal principal) {
        List<OlympiadForCalendarDTO> olympiads = olympiadForCalendarService.allForUser(principal).stream()
                .map(olympiadForCalendarMapper::ofcToOfcDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(olympiads);
    }


}
