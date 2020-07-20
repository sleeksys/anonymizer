package com.sleeksys.app.anonymizer.service;

import com.sleeksys.app.anonymizer.entity.SessionContext;
import com.sleeksys.app.anonymizer.exception.ResourceNotFoundException;
import com.sleeksys.app.anonymizer.repository.SessionContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionContextService {

    private SessionContextRepository sessionContextRepository;
    private EntityService entityService;

    public SessionContext findById(HttpSession session, String id) throws ResourceNotFoundException {
        List<SessionContext> list = this.entityService.findContext()
                .stream()
                .filter((context -> (
                        context.getContextId().equals(id)
                        && context.getSessionId().equals(session.getId())
                )))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            SessionContext context = list.get(0);
            if (!context.expired()) {
                return context;
            }
            throw new ResourceNotFoundException("This context is expired.");
        }
        throw new ResourceNotFoundException("No context found for id '" + id + "'.");
    }

    public String create(HttpSession session) {
        SessionContext context = new SessionContext();
        context.setSessionId(session.getId());
        this.sessionContextRepository.save(context);
        return context.getContextId();
    }
}
