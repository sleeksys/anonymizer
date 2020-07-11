package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.Token;
import com.sleeksys.app.anonymizer.exception.ResourceNotFoundException;
import com.sleeksys.app.anonymizer.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {

    private TokenRepository tokenRepository;
    private EntityService entityService;

    public Token findByValue(HttpSession session, String value) throws ResourceNotFoundException {
        List<Token> tokens = this.entityService.findTokens()
                .stream()
                .filter((token -> (
                        token.getValue().equals(value)
                        && token.getSessionId().equals(session.getId())
                )))
                .collect(Collectors.toList());
        if (!tokens.isEmpty()) {
            Token token = tokens.get(0);
            if (!token.expired()) {
                return token;
            }
            throw new ResourceNotFoundException("This token is expired.");
        }
        throw new ResourceNotFoundException("No token found for value '" + value + "'.");
    }

    public String insert(HttpSession session) {
        Token token = new Token();
        token.setSessionId(session.getId());
        this.tokenRepository.save(token);
        return token.getValue();
    }
}
