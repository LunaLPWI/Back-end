package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.assessment.AssessmentRequest;
import com.luna.luna_project.dtos.assessment.AssessmentResponse;
import com.luna.luna_project.models.Assessment;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.EstablishmentRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import com.luna.luna_project.services.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentMapper {

    private final EstablishmentRepository establishmentRepository;
    private final SchedulingRepository schedulingRepository;

    @Autowired
    public AssessmentMapper(EstablishmentRepository establishmentRepository, SchedulingRepository schedulingRepository) {
        this.establishmentRepository = establishmentRepository;
        this.schedulingRepository = schedulingRepository;
    }



    public AssessmentResponse toResponse(Assessment assessment) {
        AssessmentResponse response = new AssessmentResponse();
        response.setAssessment_id(assessment.getAssessment_id());
        response.setClientName(assessment.getScheduling().getClient().getName());
        response.setEstablishmentName(assessment.getEstablishment().getName());
        response.setRating(assessment.getRating());
        return response;
    }



    public Assessment toEntity(AssessmentRequest request, Establishment establishment, Scheduling scheduling) {
        return Assessment.builder()
                .rating(request.getRating())
                .messaging(request.getMessaging())
                .establishment(establishment)
                .scheduling(scheduling)
                .build();
    }



}
