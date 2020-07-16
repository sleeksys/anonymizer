package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.enumeration.PrivacyLevel;
import com.sleeksys.app.anonymizer.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class LabelController {

    private LabelService labelService;

    @GetMapping("/labels/{contextId}")
    public List<Label> findByContext(HttpSession session, @PathVariable String contextId) {
        return this.labelService.findByContext(session, contextId);
    }

    @PostMapping("/labels/{contextId}/{id}")
    public Label update(HttpSession session,
                        @PathVariable String contextId,
                        @PathVariable Long id,
                        @RequestBody PrivacyLevel level) {
        return this.labelService.update(session, contextId, id, level);
    }
}
