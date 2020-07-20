package com.sleeksys.app.anonymizer.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Entity
@Data
@Table(name = "session_contexts")
public class SessionContext {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "session_id")
    private String sessionId;

    @NotNull
    @Column(name = "context_id")
    private String contextId;

    @NotNull
    @Column(name = "date_start")
    private Date dateStart;

    @NotNull
    @Column(name = "date_end")
    private Date dateEnd;

    public SessionContext() {
        this.contextId = generateValue(30);
        this.generateValidationDate();
    }

    private void generateValidationDate() {
        Calendar calendar = Calendar.getInstance();

        this.dateStart = calendar.getTime();
        calendar.add(1, Calendar.HOUR);
        this.dateEnd = calendar.getTime();
    }

    private String generateValue(int length) {
        String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890-_";
        StringBuilder token = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public Boolean expired() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTime().getTime() > dateEnd.getTime());
    }
}
