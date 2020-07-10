package com.sleeksys.app.anonymizer.controller;

import com.sleeksys.app.anonymizer.service.CellService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CellController {

    private CellService cellService;

    @GetMapping("/sheet/{token}")
    public Map<Integer, List<String>> findByToken(@PathVariable String tokenValue) {
        return this.cellService.findByToken(tokenValue);
    }

    @PutMapping("/upload/{token}")
    public Map<Integer, List<String>> insert(@PathVariable String tokenValue, @RequestParam("file") MultipartFile file) {
        return this.cellService.insert(tokenValue, file);
    }
}
