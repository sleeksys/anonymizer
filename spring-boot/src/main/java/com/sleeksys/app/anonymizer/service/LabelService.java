package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {

    private LabelRepository labelRepository;
    private EntityService entityService;

    public List<Label> findAll() {
        return this.entityService.findLabels();
    }
}
