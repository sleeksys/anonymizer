package com.sleeksys.app.anonymizer.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "session_contexts")
public class SessionContext {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    @Column(name = "session_id")
    private String sessionId;

    @NotNull
    @Column(name = "date_start")
    private Date dateStart;

    @NotNull
    @Column(name = "date_end")
    private Date dateEnd;

    public SessionContext() {
        this.generateValidationDate();
    }

    public Boolean expired() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTime().getTime() > dateEnd.getTime());
    }

    private void generateValidationDate() {
        Calendar calendar = Calendar.getInstance();

        this.dateStart = calendar.getTime();
        calendar.add(1, Calendar.HOUR);
        this.dateEnd = calendar.getTime();
    }
}
