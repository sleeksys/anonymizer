package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.service.Impl.CellService;
import com.sleeksys.app.anonymizer.service.Impl.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class LabelController {

    private LabelService labelService;

    private CellService cellService;

    private RepositoryRestConfiguration restConfiguration;


/*    @GetMapping("/labels")
    public ResponseEntity<List<Label>> findAllLabels() {
        return this.labelService.findAll();
    }*/

/*    @GetMapping("/labels/{id}")
    public  ResponseEntity<Label> findLabelById(@PathVariable(name = "id") Long id){
        return this.labelService.findLabelById(id);
    }*/


/*    @DeleteMapping("labels")
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
    }*/

    @PostMapping("/uploadFile")
    public void insert(@RequestParam("file") MultipartFile file) throws IOException {
        List<Label> labels = this.cellService.extractLabelsFromExcelSheet(file.getInputStream(),1L);
        restConfiguration.exposeIdsFor(Cell.class, Label.class);
        this.labelService.saveLabels(labels);
        for (Label label:labels){
            this.cellService.saveCells(label.getCells());
        }

        System.out.println("I am DONE");
    }
}
