package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.entity.Label;
import com.sleeksys.app.anonymizer.service.CellService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CellController {

    private CellService cellService;

    @GetMapping("/sheet/{contextId}")
    public Map<Integer, List<String>> findByContext(HttpSession session, @PathVariable String contextId) {
        return this.cellService.findByContext(session, contextId);
    }

    @GetMapping("/download/{contextId}")
    public ResponseEntity<?> download(HttpSession session, @PathVariable String contextId) {
        return this.cellService.download(session, contextId);
    }

    @PostMapping("/upload/{contextId}")
    public List<Label> insert(HttpSession session,
                              @PathVariable String contextId,
                              @RequestParam("file") MultipartFile file) throws Exception {
        return this.cellService.insert(session, contextId, file);
    }
}
