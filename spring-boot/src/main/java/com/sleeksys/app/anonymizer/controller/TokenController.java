package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @GetMapping("/tokens/{token}")
    public Token findByValue(@PathVariable String token) {
        return this.tokenService.findByValue(token);
    }

    @PutMapping("/tokens")
    public String insert() {
        return this.tokenService.insert();
    }
}
