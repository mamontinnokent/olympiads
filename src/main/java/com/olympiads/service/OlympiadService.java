package com.olympiads.service;

import com.olympiads.dto.all.OlympiadDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.entity.User;
import com.olympiads.entity.enums.Role;
import com.olympiads.exceptions.OlympiadExistException;
import com.olympiads.dto.request.OlympiadRequest;
import com.olympiads.mapper.OlympiadMapper;
import com.olympiads.repository.OlympiadForCalendarRepository;
import com.olympiads.repository.OlympiadRepository;
import com.olympiads.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class OlympiadService {

    private final UserRepository userRepository;
    private final OlympiadMapper olympiadMapper;
    private final OlympiadRepository olympiadRepository;
    private final OlympiadForCalendarRepository olympiadForCalendarRepository;

    public List<Olympiad> getAllOlympiad() {
        return olympiadRepository.findAll();
    }

    public Olympiad updateOlympiad(OlympiadDTO olympiadDTO, Principal principal) {
        User currentUser = getCurrentUser(principal);
        Olympiad olympiad = olympiadRepository.getById(olympiadDTO.getId());

        if (currentUser.getCreatedByMe().contains(olympiad) || currentUser.getRole() == Role.ROLE_ADMIN) {
            olympiad = olympiadMapper.olympiadDTOToOlympiad(olympiadDTO);
        }

        return olympiadRepository.save(olympiad);
    }

    public List<Olympiad> forUser(Principal principal) {
        User user = getCurrentUser(principal);
        return olympiadForCalendarRepository
                .findAllByUserOrderByDateOlympiad(user).stream()
                .map(ofc -> olympiadRepository.getById(ofc.getOlympiadId())).collect(Collectors.toList());
    }

    public Optional<Olympiad> create(OlympiadRequest olympiadRequest, Principal principal) {
        User currentUser = getCurrentUser(principal);
        Olympiad olympiad = null;

        if (currentUser.getRole() == Role.ROLE_TEACHER || currentUser.getRole() == Role.ROLE_ADMIN) {
            olympiad = olympiadMapper.olympiadRequestToOlympiad(olympiadRequest, currentUser);
            log.info("Saving Olympiad {}", olympiad.getTitle());
            olympiadRepository.save(olympiad);
        }

        return Optional.of(olympiad);
    }

    public Olympiad getById(Long id) {
        return olympiadRepository.findById(id)
                .orElseThrow(() -> new OlympiadExistException("Olympiad with id " + id + " doesn't exist."));
    }


    public boolean delete(Long id, Principal principal) {
        Olympiad olympiad = olympiadRepository.getById(id);
        User user = getCurrentUser(principal);
        boolean isDeleted = user.getCreatedByMe().remove(olympiad);

        if (isDeleted || user.getRole() == Role.ROLE_ADMIN) {
            if (!isDeleted)
                olympiad.getCreator()
                        .getCreatedByMe()
                        .remove(olympiad);

            olympiadRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }
}