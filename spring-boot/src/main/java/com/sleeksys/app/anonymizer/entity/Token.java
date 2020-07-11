package com.sleeksys.app.anonymizer.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "session_id")
    private String sessionId;

    @NotNull
    @Column(unique = true)
    private String value;

    @NotNull
    @Column(name = "date_start")
    private Date dateStart;

    @NotNull
    @Column(name = "date_end")
    private Date dateEnd;

    public Token() {
        Calendar calendar = Calendar.getInstance();
        this.dateStart = calendar.getTime();
        calendar.add(1, Calendar.HOUR);
        this.dateEnd = calendar.getTime();

        SecureRandom random = new SecureRandom();
        long longToken = Math.abs(random.nextLong());
        this.value = Long.toString(longToken, 16);
    }

    public Boolean expired() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTime().getTime() > dateEnd.getTime());
    }
}
