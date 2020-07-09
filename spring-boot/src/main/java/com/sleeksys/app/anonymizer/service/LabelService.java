package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.model.Label;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {

    private LabelRepository labelRepository;

    public List<Label> findAll() {
        return this.labelRepository.findAll();
    }
}
