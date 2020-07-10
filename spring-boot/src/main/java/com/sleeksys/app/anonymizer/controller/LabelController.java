package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LabelController {

    private LabelService labelService;

    @GetMapping("/hello")
    public List<Label> findAll() {
        return this.labelService.findAll();
    }
}
