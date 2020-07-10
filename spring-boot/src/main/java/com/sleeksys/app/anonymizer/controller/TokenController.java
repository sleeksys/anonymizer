package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @GetMapping("/tokens/{token}")
    public Token findByValue(@PathVariable String token) {
        return this.tokenService.findByValue(token);
    }

    @PostMapping("/tokens")
    public String insert() {
        return this.tokenService.insert();
    }
}
