package com.olympiads.web;


import com.olympiads.dto.OlympiadDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.facade.OlympiadFacade;
import com.olympiads.payload.request.OlympiadRequest;
import com.olympiads.payload.response.MessageResponse;
import com.olympiads.service.CommentService;
import com.olympiads.service.OlympiadService;
import com.olympiads.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/olympiad")
public class OlympiadContoller {

    @Autowired
    private OlympiadService olympiadService;
    @Autowired
    private OlympiadFacade olympiadFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    public ResponseEntity<Object> getAllOlympiad() {
        List<OlympiadDTO> olympiads = olympiadService.getAllOlympiad().stream()
                .map(olympiadFacade::olympiadToOlympiadDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(olympiads, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Olympiad> getOlympiadById(@PathVariable String id) {
        Olympiad olympiad = olympiadService.getOlympiadById(Long.parseLong(id));

        return new ResponseEntity<>(olympiad, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOlympiad(@Valid @RequestBody OlympiadRequest olympiadRequest, Principal principal) {
        Optional<Olympiad> olympiad = olympiadService.createOlympiad(olympiadRequest, principal);
        OlympiadDTO olympiadDTO = olympiad.map(olympiadFacade::olympiadToOlympiadDTO).get();

        if (!olympiad.isEmpty()) return new ResponseEntity<>(olympiadDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(new MessageResponse("You don't have permissions or olympiad with this title already exist."), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteOlympiad(@PathVariable String id, Principal principal) {
        boolean flag = olympiadService.deleteOlympiad(Long.parseLong(id), principal);

        if (flag) return ResponseEntity.ok(new MessageResponse("Olympiad was deleted"));
        else return new ResponseEntity<>(new MessageResponse("Olympiad was deleted"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Olympiad> olympiadUpdate(@RequestBody OlympiadDTO olympiadDTO, Principal principal) {
        Olympiad olympiad = olympiadService.updateOlympiad(olympiadDTO, principal);
        return ResponseEntity.ok(olympiad);
    }

    @GetMapping("/my")
    public ResponseEntity<Object> getAllOlympiadForMe(Principal principal) {
        List<OlympiadDTO> olympiads = olympiadService.forUser(principal).stream()
                .map(olympiadFacade::olympiadToOlympiadDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(olympiads, HttpStatus.OK);
    }

}
