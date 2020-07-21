package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.service.Impl.CellService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CellController {

    private CellService cellService;

    @GetMapping("/sheet/{token}")
    public Map<Integer, List<String>> findByToken(HttpSession session, @PathVariable String token) {
        return this.cellService.findByToken(session, token);
    }

    @PostMapping("/upload/{token}")
    public Map<Integer, List<String>> insert(HttpSession session, @PathVariable(name = "token") String token, @RequestParam("file") MultipartFile file) throws Exception {
        return this.cellService.insert(session, token, file);
    }
}
