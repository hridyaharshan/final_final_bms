package com.benchmgmt.service;

import com.benchmgmt.entity.Assessment;
import com.benchmgmt.entity.Trainer;
import com.benchmgmt.repository.AssessmentRepository;
import com.benchmgmt.repository.TrainerRepository;
import com.benchmgmt.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final TrainerRepository trainerRepository;
    private final JwtService jwtService;

    public Assessment create(Assessment assessment, HttpServletRequest request) {
        // Extract email from JWT token in Authorization header
        String token = jwtService.extractTokenFromRequest(request);
        String email = jwtService.extractUsername(token);

        // Find trainer using the email
        Trainer trainer = trainerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        // Set the trainer to the assessment
        assessment.setTrainer(trainer);

        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAll() {
        return assessmentRepository.findAll();
    }

    public Assessment update(Integer id, Assessment updatedAssessment) {
        Optional<Assessment> optional = assessmentRepository.findById(id);
        if (optional.isPresent()) {
            Assessment assessment = optional.get();
            assessment.setAssessmentLink(updatedAssessment.getAssessmentLink());
            assessment.setTopic(updatedAssessment.getTopic());
            assessment.setCreatedDate(updatedAssessment.getCreatedDate());
            return assessmentRepository.save(assessment);
        }
        return null;
    }

    public void delete(Integer id) {
        assessmentRepository.deleteById(id);
    }
}

