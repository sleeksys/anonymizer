package com.sleeksys.app.anonymizer.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "cells")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "token_id")
    private Long tokenId;

    @NotNull
    @Column(name = "row_index")
    private Integer rowIndex;

    @NotNull
    @Column(name = "column_index")
    private Integer columnIndex;

    private String text;
}
