package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.assessment.AssessmentRequest;
import com.luna.luna_project.dtos.assessment.AssessmentResponse;
import com.luna.luna_project.mapper.AssessmentMapper;
import com.luna.luna_project.models.Assessment;
import com.luna.luna_project.services.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService service;

    @Autowired
    private AssessmentMapper mapper;

    @PostMapping
    public ResponseEntity<AssessmentResponse> save(@RequestBody @Valid AssessmentRequest request) {
        Assessment saved = service.saveAssessment(request);
        AssessmentResponse response = mapper.toResponse(saved);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AssessmentResponse>> getAllByEstablishmentId(@RequestParam Long establishmentId) {
        List<Assessment> assessments = service.getAssessmentsByEstablishmentId(establishmentId);
        List<AssessmentResponse> responseList = assessments.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(responseList);
    }

}
