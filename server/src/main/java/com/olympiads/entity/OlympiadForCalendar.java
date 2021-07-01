package com.olympiads.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OlympiadForCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    private Long olympiadId;
    private String olympiadName;
    private LocalDate dateOlympiad;
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OlympiadForCalendar)) return false;
        OlympiadForCalendar that = (OlympiadForCalendar) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(olympiadId, that.olympiadId) && Objects.equals(olympiadName, that.olympiadName) && Objects.equals(dateOlympiad, that.dateOlympiad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, olympiadId, olympiadName, dateOlympiad);
    }
}
