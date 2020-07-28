package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Cell;
import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.enumeration.PrivacyLevel;
import com.sleeksys.app.anonymizer.exception.ResourceNotFoundException;
import com.sleeksys.app.anonymizer.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabelService {

    private LabelRepository labelRepository;

    private EntityService entityService;
    private CellService cellService;
    private SessionContextService contextService;

    public List<Label> findByContext(HttpSession session, String contextId) {
        // this call throws an exception if context's id don't match session
        this.contextService.findById(session, contextId);

        List<Label> result = new ArrayList<>();
        List<Label> labels = this.entityService.findLabels();
        for (Label label: labels) {
            Optional<Cell> optionalCell = this.cellService.findById(label.getCellId());
            if (optionalCell.isPresent()) {
                Cell cell = optionalCell.get();
                if (cell.getContextId().equals(contextId)) {
                    result.add(label);
                }
            }
        }
        return result;
    }

    public Label insert(Label label) {
        return this.labelRepository.save(label);
    }

    public Label update(HttpSession session, String contextId, Long id, PrivacyLevel level) {
        Optional<Label> optional = this.labelRepository.findById(id);
        if (optional.isPresent()) {
            Label label = optional.get();

            Optional<Cell> optionalCell = this.cellService.findById(label.getCellId());
            if (optionalCell.isPresent()) {
                Cell cell = optionalCell.get();
                if (cell.getContextId().equals(contextId)) {
                    // this call throws an exception if context's id don't match session
                    this.contextService.findById(session, contextId);

                    // update privacy level
                    label.setPrivacyLevel(level);

                    // anonymize cell's text
                    this.cellService.anonymizeCellText(contextId, label.getCellId(), level);

                    return this.labelRepository.save(label);
                }
                throw new ResourceNotFoundException("Authorization failed for sheet's cell.");
            }
            throw new ResourceNotFoundException("Could not match label's cell in sheet.");
        }
        throw new ResourceNotFoundException("No label found for id '" + id + "'.");
    }
}
