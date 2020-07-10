package com.sleeksys.app.anonymizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Integer sheetColumn;

    private PrivacyLevel privacyLevel;

    public Label(String text) {
        this.text = text;
        this.sheetColumn = 1;
        this.privacyLevel = PrivacyLevel.MEDIUM;
    }

    public Label(String text, Integer sheetColumn) {
        this.text = text;
        this.sheetColumn = sheetColumn;
        this.privacyLevel = PrivacyLevel.MEDIUM;
    }
}
