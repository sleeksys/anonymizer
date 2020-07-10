package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.repository.CellRepository;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import com.sleeksys.app.anonymizer.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EntityService {

    private TokenRepository tokenRepository;
    private CellRepository cellRepository;
    private LabelRepository labelRepository;

    public List<Token> findTokens() {
        List<Token> tokens = new ArrayList<>();
        this.tokenRepository.findAll().forEach(token -> {
            tokens.add(token);
        });
        return tokens;
    }

    public List<Cell> findCells() {
        List<Cell> cells = new ArrayList<>();
        this.cellRepository.findAll(
                Sort.by("row_index").ascending()
                        .and(Sort.by("column_index").ascending())
        ).forEach(cell -> {
            cells.add(cell);
        });
        return cells;
    }

    public List<Label> findLabels() {
        List<Label> labels = new ArrayList<>();
        this.labelRepository.findAll(
                Sort.by("id").ascending()
        ).forEach(label -> {
            labels.add(label);
        });
        return labels;
    }
}
