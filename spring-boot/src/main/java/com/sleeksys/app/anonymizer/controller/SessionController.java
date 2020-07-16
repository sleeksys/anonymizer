package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.SessionContext;
import com.sleeksys.app.anonymizer.service.SessionContextService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class SessionController {

    private SessionContextService sessionContextService;

    @GetMapping("/context/{id}")
    public SessionContext findById(HttpSession session, @PathVariable String id) {
        return this.sessionContextService.findById(session, id);
    }

    @PostMapping("/context")
    public String create(HttpSession session) {
        return this.sessionContextService.create(session);
    }
}
