package com.olympiads.web;


import com.olympiads.dto.all.OlympiadDTO;
import com.olympiads.dto.request.OlympiadRequest;
import com.olympiads.dto.response.MessageResponse;
import com.olympiads.entity.Olympiad;
import com.olympiads.mapper.OlympiadMapper;
import com.olympiads.service.OlympiadService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("api/olympiad")
public class OlympiadContoller {

    private final OlympiadMapper olympiadMapper;
    private final OlympiadService olympiadService;


    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        List<OlympiadDTO> olympiads = olympiadService.getAllOlympiad().stream()
                .map(olympiadMapper::olympiadToOlympiadDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(olympiads, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Olympiad> getById(@PathVariable String id) {
        Olympiad olympiad = olympiadService.getById(Long.parseLong(id));

        return new ResponseEntity<>(olympiad, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody OlympiadRequest olympiadRequest, Principal principal) {
        Optional<Olympiad> olympiad = olympiadService.create(olympiadRequest, principal);
        OlympiadDTO olympiadDTO = olympiad.map(olympiadMapper::olympiadToOlympiadDTO).get();

        if (!olympiad.isEmpty()) return new ResponseEntity<>(olympiadDTO, HttpStatus.OK);
        else return new ResponseEntity<>(new MessageResponse("You don't have permissions or olympiad with this title already exist."), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable String id, Principal principal) {
        boolean flag = olympiadService.delete(Long.parseLong(id), principal);

        if (flag) return ResponseEntity.ok(new MessageResponse("Olympiad was deleted"));
        else return new ResponseEntity<>(new MessageResponse("Olympiad was deleted"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Olympiad> update(@RequestBody OlympiadDTO olympiadDTO, Principal principal) {
        Olympiad olympiad = olympiadService.updateOlympiad(olympiadDTO, principal);
        return ResponseEntity.ok(olympiad);
    }

    @GetMapping("/my")
    public ResponseEntity<Object> getAllOlympiadForMe(Principal principal) {
        List<OlympiadDTO> olympiads = olympiadService.forUser(principal).stream()
                .map(olympiadMapper::olympiadToOlympiadDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(olympiads, HttpStatus.OK);
    }

}
