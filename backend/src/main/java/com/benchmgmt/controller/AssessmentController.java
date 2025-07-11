package com.benchmgmt.controller;

import com.benchmgmt.dto.AssessmentResponseDTO;
import com.benchmgmt.entity.Assessment;
import com.benchmgmt.service.AssessmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bms/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentResponseDTO> createAssessment(@RequestBody Assessment assessment,
                                                                  HttpServletRequest request) {
        Assessment saved = assessmentService.create(assessment, request);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping
    public List<AssessmentResponseDTO> getAll() {
        return assessmentService.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public AssessmentResponseDTO update(@PathVariable Integer id,
                                        @RequestBody Assessment assessment) {
        Assessment updated = assessmentService.update(id, assessment);
        return toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        assessmentService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // 🔁 Helper method to map entity to DTO
    private AssessmentResponseDTO toDto(Assessment assessment) {
        return new AssessmentResponseDTO(
                assessment.getAssessmentId(),
                assessment.getTopic(),
                assessment.getAssessmentLink(),
                assessment.getCreatedDate(),
                assessment.getTrainer().getEmpId(),
                assessment.getTrainer().getName()
        );
    }
}


