package com.isteamx.university.controller;

import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping("/user/{id}")
    public ProfessorDTO getProfessor(@PathVariable Long id) {
        return professorService.getProfessor(id);
    }

    @GetMapping("/user")
    public List<ProfessorDTO> getProfessors() {
        return professorService.getProfessors();
    }

    @PutMapping()
    public ResponseEntity<Map<String,String>> updateProfessor(@RequestBody ProfessorDTO professorDTO) {
        professorService.updateProfessor(professorDTO);
        return ResponseEntity.ok(Map.of("Message","Professor updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.ok(Map.of("Message","Professor deleted"));
    }

}
