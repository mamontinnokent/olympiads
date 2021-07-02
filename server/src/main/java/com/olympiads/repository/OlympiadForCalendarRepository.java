package com.olympiads.repository;

import com.olympiads.entity.OlympiadForCalendar;
import com.olympiads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OlympiadForCalendarRepository extends JpaRepository<OlympiadForCalendar, Long> {

    List<OlympiadForCalendar> findAllByUser(User user);
    List<OlympiadForCalendar> findAllByUserOrderByDateOlympiadDesc(User user);
    void deleteById(Long id);

    boolean deleteAllByOlympiadId(Long id);

}
