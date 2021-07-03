package com.olympiads.service;

import com.olympiads.dto.all.OlympiadForCalendarDTO;
import com.olympiads.entity.Olympiad;
import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.entity.User;
import com.olympiads.repository.OlympiadForCalendarRepository;
import com.olympiads.repository.OlympiadRepository;
import com.olympiads.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OlympiadForCalendarService {

    private final UserRepository userRepository;
    private final OlympiadRepository olympiadRepository;
    private final OlympiadForCalendarRepository olympiadForCalendarRepository;

    public boolean addToUser(OlympiadForCalendarDTO olympiadForCalendarDTO, Principal principal) {
        User user = getCurrentUser(principal);

        Olympiad olympiad = olympiadRepository.getById(olympiadForCalendarDTO.getOlympiadId());
        OlympiadForCalendar olympiadForCalendar = new OlympiadForCalendar();
        olympiadForCalendar.setUser(user);
        olympiadForCalendar.setOlympiadName(olympiad.getTitle());
        olympiadForCalendar.setDateOlympiad(olympiad.getDateOfOlympiad());
        olympiadForCalendar.setOlympiadId(olympiad.getId());

        if (olympiadForCalendarRepository.findAllByUser(user).add(olympiadForCalendar)) {
            olympiadForCalendarRepository.save(olympiadForCalendar);
            return true;
        }

        return false;
    }

    public List<OlympiadForCalendar> allForUser(Principal principal) {
        User user = getCurrentUser(principal);
        return olympiadForCalendarRepository.findAllByUserOrderByDateOlympiad(user);
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
