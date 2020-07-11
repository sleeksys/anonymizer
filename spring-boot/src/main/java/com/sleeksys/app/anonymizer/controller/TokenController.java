package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @GetMapping("/tokens")
    public String insert(HttpSession session) {
        return this.tokenService.insert(session);
    }

    @GetMapping("/tokens/{token}")
    public Token findByValue(HttpSession session, @PathVariable String token) {
        return this.tokenService.findByValue(session, token);
    }
}
