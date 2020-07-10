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

    public List<Label> findAll() {
        this.labelRepository.save(new Label("Text 1"));
        this.labelRepository.save(new Label("Text 2"));
        this.labelRepository.save(new Label("Text 3"));
        this.labelRepository.save(new Label("Text 4"));
        this.labelRepository.save(new Label("Text 5"));

        List<Label> labels = new ArrayList<>();
        this.labelRepository.findAll().forEach(label -> {
            labels.add(label);
        });
        return labels;
    }
}
