package com.benchmgmt.service;

import com.benchmgmt.dto.TrainingDTO;

import java.util.List;

public interface TrainingService {
    TrainingDTO saveTraining(TrainingDTO trainingDTO);
    TrainingDTO getTrainingById(Integer id);
    List<TrainingDTO> getAllTrainings();
    void deleteTraining(Integer id);
    List<TrainingDTO> getTrainingsByEmpId(Integer empId);
}
