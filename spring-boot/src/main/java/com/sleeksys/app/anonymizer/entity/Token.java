package com.sleeksys.app.anonymizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String value;

    private Date start;
    private Date end;

    public Token() {
        Calendar calendar = Calendar.getInstance();
        this.start = calendar.getTime();
        calendar.add(1, Calendar.HOUR);
        this.end = calendar.getTime();
    }

    public Boolean expired() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTime().getTime() > end.getTime());
    }
}
