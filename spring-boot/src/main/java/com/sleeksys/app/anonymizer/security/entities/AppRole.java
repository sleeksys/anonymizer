package com.sleeksys.app.anonymizer.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AppRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
}
