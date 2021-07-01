package com.olympiads.service;

import com.olympiads.dto.OlympiadDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.entity.User;
import com.olympiads.entity.enums.Role;
import com.olympiads.exceptions.OlympiadExistException;
import com.olympiads.payload.request.OlympiadRequest;
import com.olympiads.repository.OlympiadForCalendarRepository;
import com.olympiads.repository.OlympiadRepository;
import com.olympiads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OlympiadService {

    private final OlympiadRepository olympiadRepository;
    private final UserRepository userRepository;
    private final OlympiadForCalendarRepository olympiadForCalendarRepository;


    public OlympiadService(OlympiadRepository olympiadRepository, UserRepository userRepository, OlympiadForCalendarRepository olympiadForCalendarRepository) {
        this.olympiadRepository = olympiadRepository;
        this.userRepository = userRepository;
        this.olympiadForCalendarRepository = olympiadForCalendarRepository;
    }

    public List<Olympiad> getAllOlympiad() {
        return olympiadRepository.findAll();
    }

    public Olympiad updateOlympiad(OlympiadDTO olympiadDTO, Principal principal) {
        User currentUser = getCurrentUser(principal);
        Olympiad olympiad = olympiadRepository.getById(olympiadDTO.getId());

        if (currentUser.getCreatedByMe().contains(olympiad) || currentUser.getRole() == Role.ROLE_ADMIN) {
            olympiad.setTitle(olympiadDTO.getTitle());
            olympiad.setCaption(olympiadDTO.getCaption());
            olympiad.setLocation(olympiadDTO.getLocation());
            olympiad.setLesson(olympiadDTO.getLesson());
        }

        return olympiadRepository.save(olympiad);
    }

    public Optional<Olympiad> createOlympiad(OlympiadRequest olympiadRequest, Principal principal) {
        User currentUser = getCurrentUser(principal);
        Olympiad olympiad = null;
        if (currentUser.getRole() == Role.ROLE_TEACHER || currentUser.getRole() == Role.ROLE_ADMIN) {
            olympiad = new Olympiad();

            olympiad.setTitle(olympiadRequest.getTitle());
            olympiad.setCaption(olympiadRequest.getCaption());
            olympiad.setLocation(olympiadRequest.getLocation());
            olympiad.setLesson(olympiadRequest.getLesson());
            olympiad.setLink(olympiadRequest.getLink());
            olympiad.setCreator(currentUser);
            currentUser.getCreatedByMe().add(olympiad);

            log.info("Saving Olympiad {}", olympiad.getTitle());
            olympiadRepository.save(olympiad);
        }
        return Optional.of(olympiad);
    }

    public Olympiad getOlympiadById(Long id) {
        return olympiadRepository.findById(id)
                .orElseThrow(() -> new OlympiadExistException("Olympiad with id " + id + " doesn't exist."));
    }

    private User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }

    public boolean deleteOlympiad(Long id, Principal principal) {
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
}