package com.luna.luna_project.dtos.assessment;

import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Scheduling;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentResponse {

    private Long assessment_id;
    private String establishmentName;
    private String clientName;
    private Double rating;
    private String messaging;
}
