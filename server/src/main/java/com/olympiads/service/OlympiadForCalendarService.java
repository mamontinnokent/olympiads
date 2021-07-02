package com.olympiads.service;

import com.olympiads.dto.OlympiadDTO;
import com.olympiads.dto.OlympiadForCalendarDTO;
import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.entity.User;
import com.olympiads.repository.OlympiadForCalendarRepository;
import com.olympiads.repository.UserRepository;
import com.olympiads.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class OlympiadForCalendarService {

    private final UserRepository userRepository;
    private final OlympiadForCalendarRepository olympiadForCalendarRepository;

    @Autowired
    public OlympiadForCalendarService(UserRepository userRepository, OlympiadForCalendarRepository olympiadForCalendarRepository) {
        this.userRepository = userRepository;
        this.olympiadForCalendarRepository = olympiadForCalendarRepository;
    }

    public void addToUser(OlympiadForCalendarDTO olympiadForCalendarDTO, Principal principal) {
        User user = getCurrentUser(principal);

        OlympiadForCalendar olympiad = new OlympiadForCalendar();

        olympiad.setUser(user);
        olympiad.setOlympiadName(olympiadForCalendarDTO.getOlympiadName());
        olympiad.setDateOlympiad(Utility.stringToTimestamp(olympiadForCalendarDTO.getDateOlympiad()));
        olympiad.setOlympiadId(olympiadForCalendarDTO.getOlympiadId());

        olympiadForCalendarRepository.save(olympiad);
    }

    public List<OlympiadForCalendar> allForUser(Principal principal) {
        User user = getCurrentUser(principal);
        return olympiadForCalendarRepository.findAllByUserOrderByDateOlympiadDesc(user);
    }


    public void deleteForUser(Long id, Principal principal) {
        User user = getCurrentUser(principal);
        OlympiadForCalendar olympiad = olympiadForCalendarRepository.findById(id).get();
        user.getOlympiads().remove(olympiad);
        olympiadForCalendarRepository.deleteById(id);
    }

    private User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }
}
