package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.enumeration.PrivacyLevel;
import com.sleeksys.app.anonymizer.error.ResponseEntityError;
import com.sleeksys.app.anonymizer.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class LabelController {

    private LabelService labelService;

    @GetMapping("/labels/{contextId}")
    public ResponseEntity<?> findByContext(HttpServletRequest servletRequest,
                                           HttpSession session,
                                           @PathVariable String contextId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.labelService.findByContext(session, contextId));
        } catch (Exception exception) {
            return ResponseEntityError.getResponse(servletRequest, exception);
        }
    }

    @PostMapping("/labels/{contextId}/{id}")
    public ResponseEntity<?> update(HttpServletRequest servletRequest,
                                    HttpSession session,
                                    @PathVariable String contextId,
                                    @PathVariable Long id,
                                    @RequestBody PrivacyLevel level) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.labelService.update(session, contextId, id, level));
        } catch (Exception exception) {
            return ResponseEntityError.getResponse(servletRequest, exception);
        }
    }
}
