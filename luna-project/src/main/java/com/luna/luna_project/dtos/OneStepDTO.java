package com.luna.luna_project.dtos;

import com.luna.luna_project.enums.Plans;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OneStepDTO {
    private Long id;
    private String subscription_id;
    private Integer total;
    private Plans chargeRequest;
    private String payment;
    private LocalDateTime first_execution;
    private PlanDTO plan;
    private String status;
}
