package com.luna.luna_project.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlanAndChargeRequestDTO {
    private PlanDTO planDTO;
    private ChargeRequestDTO chargeRequestDTO;
}

