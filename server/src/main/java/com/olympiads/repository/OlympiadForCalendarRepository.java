package com.olympiads.repository;

import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OlympiadForCalendarRepository extends JpaRepository<OlympiadForCalendar, Long> {

    List<OlympiadForCalendar> findAll();
    List<OlympiadForCalendar> findAllByUser(User user);
    List<OlympiadForCalendar> findAllByOlympiadIdOrderByDateOlympiadDesc();
    boolean deleteAllByOlympiadId(Long id);

}
