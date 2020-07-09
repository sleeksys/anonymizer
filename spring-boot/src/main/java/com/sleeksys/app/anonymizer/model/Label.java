package com.sleeksys.app.anonymizer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Label {
    @Id
    @JsonIgnore
    private Long id;

    private String text;

    private PrivacyLevel privacyLevel;

    public Label(String text) {
        this.text = text;
        this.privacyLevel = PrivacyLevel.MEDIUM;
    }
}
