package com.luna.luna_project.dtos.assessment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentRequest {
    private Long establishmentId;
    private Long schedulingId;
    private Double rating;
    private Double messaging;
}
