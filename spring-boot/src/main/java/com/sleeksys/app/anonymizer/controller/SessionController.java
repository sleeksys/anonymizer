package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.SessionContext;
import com.sleeksys.app.anonymizer.error.ResponseEntityError;
import com.sleeksys.app.anonymizer.service.SessionContextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class SessionController {

    private SessionContextService sessionContextService;

    @GetMapping("/context/{id}")
    public ResponseEntity<?> findById(HttpServletRequest servletRequest,
                                      HttpSession session,
                                      @PathVariable String id) {
        try {
            SessionContext context = this.sessionContextService.findById(session, id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(context);
        } catch (Exception exception) {
            return ResponseEntityError.getResponse(servletRequest, exception);
        }
    }

    @PostMapping("/context")
    public ResponseEntity<?> create(HttpServletRequest servletRequest,
                                    HttpSession session) {
        try {
            String contextId = this.sessionContextService.create(session);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(contextId);
        } catch (Exception exception) {
            return ResponseEntityError.getResponse(servletRequest, exception);
        }
    }
}
