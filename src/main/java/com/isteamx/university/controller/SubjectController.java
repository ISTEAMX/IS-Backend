package com.isteamx.university.controller;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponseWrapper<SubjectDTO>> getSubject(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(subjectService.getSubjectById(id), "Subject retrieved successfully"));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponseWrapper<Page<SubjectDTO>>> getSubjects(Pageable pageable) {
        return ResponseEntity.ok(ApiResponseWrapper.success(subjectService.getSubjects(pageable), "Subjects retrieved successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<SubjectDTO>> createSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.created(subjectService.createSubject(subjectDTO), "Subject created successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseWrapper<Void>> updateSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        subjectService.updateSubject(subjectDTO);
        return ResponseEntity.ok(ApiResponseWrapper.success("Subject updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponseWrapper.success("Subject deleted successfully"));
    }

}
