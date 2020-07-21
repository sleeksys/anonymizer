package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Label;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ILabelService {

    /*
     * Finds all Labels from database
     *
     * */
    public  ResponseEntity<List<Label>>  findAll();

    /*
     * Saves Labels in the database
     *
     * */
    public void saveLabels(List<Label> labels);

    /*
     * Deletes Label by id
     *
     * */
    public ResponseEntity<HttpStatus> deleteLabelById(Long id);

    /*
     * Deletes all labels
     *
     * */
    public ResponseEntity<HttpStatus> deleteAllLabels();

    /*
     * Updates Label
     *
     * */
    public  ResponseEntity<Label> updateLabel( Long id, Label label);

    public ResponseEntity<Label> findLabelById(Long id);
}
