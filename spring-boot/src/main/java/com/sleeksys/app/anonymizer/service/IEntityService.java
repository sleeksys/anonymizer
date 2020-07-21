package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.entity.Token;

import java.util.List;

public interface IEntityService {

    /*
     * Finds all tokens save in the database
     *
     * */
    public List<Token> findTokens();

    /*
     * Finds all Cells save in the database
     *
     * */
    public List<Cell> findCells();

    /*
     * Finds all Labels save in the database
     *
     * */
    public List<Label> findLabels();
}
