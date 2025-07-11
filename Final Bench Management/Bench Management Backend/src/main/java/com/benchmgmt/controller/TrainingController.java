package com.benchmgmt.controller;

import com.benchmgmt.dto.TrainingDTO;
import com.benchmgmt.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bms/trainings")
public class    TrainingController {
    @Autowired
    private TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO){
        return ResponseEntity.ok(trainingService.saveTraining(trainingDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTrainingById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainingService.getTrainingById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable Integer id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.ok("Training deleted successfully");
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByEmpId(@PathVariable Integer empId) {
        return ResponseEntity.ok(trainingService.getTrainingsByEmpId(empId));
    }
}
