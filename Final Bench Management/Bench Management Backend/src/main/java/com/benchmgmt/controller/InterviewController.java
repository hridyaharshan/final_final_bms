package com.benchmgmt.controller;


import com.benchmgmt.dto.InterviewCycleDTO;
import com.benchmgmt.dto.InterviewDTO;
import com.benchmgmt.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/bms/interviews")
@RequiredArgsConstructor
@CrossOrigin
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public InterviewDTO createInterview(@RequestBody InterviewDTO dto) {
        return interviewService.saveInterview(dto);
    }

    @GetMapping
    public List<InterviewDTO> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/{id}")
    public InterviewDTO getInterviewById(@PathVariable Integer id) {
        return interviewService.getInterviewById(id);
    }

    //this for the fetchinng by the interview id
    @PutMapping("/{id}")
    public InterviewDTO updateInterview(@PathVariable Integer id, @RequestBody InterviewDTO dto) {
        return interviewService.updateInterview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteInterview(@PathVariable Integer id) {
        interviewService.deleteInterview(id);
    }





    // ✅ Get all interview cycles for a candidate
    @GetMapping("/{empId}/cycles-details")
    public List<InterviewCycleDTO> getCycles(@PathVariable Integer empId) {
        return interviewService.getInterviewCyclesByCandidateId(empId);
    }

    // ✅ Create a new interview cycle
    @PostMapping("/{empId}/cycles-add")
    public InterviewCycleDTO createCycle(@PathVariable Integer empId, @RequestBody InterviewCycleDTO dto) {
        dto.setEmpId(empId);
        return interviewService.createInterviewCycle(dto);
    }

    // ✅ Get all interview rounds in a cycle
    @GetMapping("/cycles/{cycleId}/details")
    public List<InterviewDTO> getRoundsByCycle(@PathVariable Integer cycleId) {
        return interviewService.getFullInterviewsByCycleId(cycleId);
    }

    // ✅ Add a new round to a cycle
    @PostMapping("/cycles/{cycleId}/add")
    public InterviewDTO addRoundToCycle(@PathVariable Integer cycleId, @RequestBody InterviewDTO dto) {
        dto.setCycleId((long) cycleId);
        return interviewService.saveInterviewRoundForCycle(Long.valueOf(cycleId), dto);
    }


    // ✅ DELETE: Interview cycle
    @DeleteMapping("/cycles/{cycleId}")
    public ResponseEntity<Void> deleteCycle(@PathVariable Integer cycleId) {
        interviewService.deleteInterviewCycle(cycleId);
        return ResponseEntity.noContent().build();
    }

    // ✅ DELETE: Interview round
    @DeleteMapping("/interviews/{interviewId}")
    public ResponseEntity<Void> deleteInterviewRound(@PathVariable Integer interviewId) {
        interviewService.deleteInterviewRound(interviewId);
        return ResponseEntity.noContent().build();
    }

}

