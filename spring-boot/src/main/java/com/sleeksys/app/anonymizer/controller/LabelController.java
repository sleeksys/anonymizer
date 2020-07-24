package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.service.Impl.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class LabelController {

    private LabelService labelService;


    @GetMapping("/labels")
    public ResponseEntity<List<Label>> findAllLabels() {
        return this.labelService.findAll();
    }

    @GetMapping("/labels/{id}")
    public  ResponseEntity<Label> findLabelById(@PathVariable(name = "id") Long id){
        return this.labelService.findLabelById(id);
    }


    @DeleteMapping("labels")
    public ResponseEntity<HttpStatus> deleteAllLabels(){
       return this.labelService.deleteAllLabels();
    }

    @DeleteMapping("/labels/{id}")
    public ResponseEntity<HttpStatus> deleteLabelById(@PathVariable(name = "id") Long id){
        return this.labelService.deleteLabelById(id);
    }

    @PutMapping("/labels/{id}")
    public ResponseEntity<Label>  updateLabel(@PathVariable(name = "id") Long id, @RequestBody Label label){
        return  this.labelService.updateLabel(id,label);
    }

    @PostMapping("/labels")
    public Label CreateAndSaveLabel(@RequestBody Label label){
    List<Label> labels = new ArrayList<>();
    labels.add(label);
    this.labelService.saveLabels(labels);
    return label;
    }

    @GetMapping("/labels/{id}")
    public  ResponseEntity<List<Cell>> findCellsOfLabelById(@PathVariable(name = "id") Long id){

        return this.labelService.findCellsOfLabelById(id);
    }


}
