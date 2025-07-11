package com.benchmgmt.controller;

import com.benchmgmt.dto.EmpScoreRequest;
import com.benchmgmt.dto.ScoreDetailsDTO;
import com.benchmgmt.entity.EmpScore;
import com.benchmgmt.service.EmpScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bms/scores")
public class EmpScoreController {

    @Autowired
    private EmpScoreService service;

    @PostMapping
    public EmpScore postScore(@RequestBody EmpScoreRequest request) {
        return service.addScore(request);
    }

    @GetMapping("/filter")
    public List<ScoreDetailsDTO> filterScores(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return service.getFilteredScores(topic, empId, date);
    }
}
