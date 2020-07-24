package com.sleeksys.app.anonymizer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sleeksys.app.anonymizer.enumeration.PrivacyLevel;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "cell_id")
    private Long cellId;

    @NotNull
    private String text;

    @NotNull
    @Column(name = "privacy_level")
    private PrivacyLevel privacyLevel;

    @OneToMany(mappedBy = "label")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Cell> cells;

    public Label(String text, Long cellId) {
        this.text = text;
        this.cellId = cellId;
        this.privacyLevel = PrivacyLevel.NONE;
    }
}
