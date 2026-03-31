package com.isteamx.university.controller;

import com.isteamx.university.dto.SubjectDTO;
import com.isteamx.university.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/user/{id}")
    public SubjectDTO getUser(@PathVariable Long id){
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/user")
    public List<SubjectDTO> getUser(){
        return subjectService.getSubjects();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSubject(@RequestBody SubjectDTO subjectDTO){
                subjectService.createSubject(subjectDTO);
        return ResponseEntity.ok("Subject created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSubject(@RequestBody SubjectDTO subjectDTO){
        subjectService.updateSubject(subjectDTO);
        return ResponseEntity.ok("Subject updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id){
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted");
    }

}
