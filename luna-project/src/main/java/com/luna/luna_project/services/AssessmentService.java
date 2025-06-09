package com.luna.luna_project.services;

import com.luna.luna_project.dtos.assessment.AssessmentRequest;
import com.luna.luna_project.dtos.assessment.AssessmentUpdateDTO;
import com.luna.luna_project.mapper.AssessmentMapper;
import com.luna.luna_project.models.Assessment;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Scheduling;
import com.luna.luna_project.repositories.AssessmentRepository;
import com.luna.luna_project.repositories.EstablishmentRepository;
import com.luna.luna_project.repositories.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository repository;
    @Autowired
    private AssessmentMapper mapper;
    @Autowired
    private SchedulingRepository schedulingRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;

    public Assessment saveAssessment(AssessmentRequest request) {
        Establishment establishment = establishmentRepository.findById(request.getEstablishmentId())
                .orElseThrow(() -> new IllegalArgumentException("Estabelecimento não encontrado"));

        Scheduling scheduling = schedulingRepository.findById(request.getSchedulingId())
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        Assessment assessment = mapper.toEntity(request, establishment, scheduling);
        return repository.save(assessment);
    }

    public List<Assessment> getAssessmentsByEstablishmentId(Long establishmentId) {
        return repository.findAllByEstablishment_Id(establishmentId);
    }

    public List<Assessment> getPastAssessmentsByClientId(Long clientId,LocalDateTime currentDateTime ) {
        return repository.findAssessmentsByClientIdAndPastScheduling(clientId, currentDateTime);
    }

    public Optional<Assessment> updateAssessment(Long id, AssessmentUpdateDTO dto) {
        Optional<Assessment> optionalAssessment = repository.findById(id);

        if (optionalAssessment.isPresent()) {
            Assessment assessment = optionalAssessment.get();
            if (dto.getRating() != null) {
                assessment.setRating(dto.getRating());
            }
            if (dto.getMessaging() != null) {
                assessment.setMessaging(dto.getMessaging());
            }
            repository.save(assessment);
            return Optional.of(assessment);
        }

        return Optional.empty();
    }

}
