package com.benchmgmt.service.impl;

import com.benchmgmt.dto.TrainingDTO;
import com.benchmgmt.entity.Candidate;
import com.benchmgmt.entity.Training;
import com.benchmgmt.repository.CandidateRepository;
import com.benchmgmt.repository.TrainingRepository;
import com.benchmgmt.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private TrainingDTO mapToDTO(Training training){
        return new TrainingDTO(
                training.getTrainingId(),
                training.getStartDate(),
                training.getEndDate(),
                training.getMentor(),
                training.getFeedback(),
                training.getTopics(),
                training.getCandidate().getEmpId()
        );
    }

    private Training mapToEntity(TrainingDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + dto.getEmpId()));

        return new Training(
                dto.getTrainingId(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getMentor(),
                dto.getFeedback(),
                dto.getTopics(),
                candidate
        );
    }

    @Override
    public TrainingDTO saveTraining(TrainingDTO trainingDTO) {
        Training saved = trainingRepository.save(mapToEntity(trainingDTO));
        return mapToDTO(saved);
    }

    @Override
    public TrainingDTO getTrainingById(Integer id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training not found"));
        return mapToDTO(training);
    }

    @Override
    public List<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTraining(Integer id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public List<TrainingDTO> getTrainingsByEmpId(Integer empId) {
        List<Training> trainings = trainingRepository.findByCandidateEmpId(empId);
        return trainings.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
