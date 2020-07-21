package com.sleeksys.app.anonymizer.service.Impl;

import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import com.sleeksys.app.anonymizer.service.ILabelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabelService implements ILabelService {

    private LabelRepository labelRepository;
    private EntityService entityService;


    /*
     * Finds all Labels from database
     *
     * */
    public ResponseEntity<List<Label>> findAll() {

        List<Label> labels = new ArrayList<>();

        try {
            this.entityService.findLabels().forEach(label -> labels.add(label));

            if (labels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labels, HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
     * Saves Labels in the database
     *
     * */
    @Override
    public void saveLabels(List<Label> labels) {
        if(labels.size() >= 1){
            for (Label label: labels) {
                labelRepository.save(label);
            }
        }
    }

    /*
     * Deletes Label by id
     *
     * */
    @Override
    public ResponseEntity<HttpStatus> deleteLabelById(Long id) {

        try {
            this.labelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /*
     * Deletes all labels
     *
     * */
    @Override
    public ResponseEntity<HttpStatus> deleteAllLabels() {

        try {
            this.labelRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /*
     * Updates Label
     *
     * */
    @Override
    public  ResponseEntity<Label> updateLabel(Long id, Label label) {
        Optional<Label> labelData = labelRepository.findById(id);

        if (labelData.isPresent()) {
            Label _label = labelData.get();
            _label.setPrivacyLevel(label.getPrivacyLevel());
            _label.setText(label.getText());
            _label.setCellId(label.getCellId());
            return new ResponseEntity<>(labelRepository.save(_label), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Label> findLabelById(Long id) {
        Optional<Label> labelData = labelRepository.findById(id);

        if (labelData.isPresent()) {
            return new ResponseEntity<>(labelData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
