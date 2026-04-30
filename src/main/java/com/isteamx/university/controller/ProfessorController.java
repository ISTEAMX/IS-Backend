package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.ProfessorDTO;
import com.isteamx.university.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseWrapper<ProfessorDTO>> getProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(professorService.getProfessor(id), "Professor retrieved successfully"));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponseWrapper<Page<ProfessorDTO>>> getProfessors(Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(professorService.getProfessors(pageable), "Professors retrieved successfully"));
    }

    @PutMapping()
    public ResponseEntity<ApiResponseWrapper<Void>> updateProfessor(@Valid @RequestBody ProfessorDTO professorDTO) {
        professorService.updateProfessor(professorDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Professor updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.ok(ApiResponseWrapper.success("Professor deleted successfully"));
    }

}
