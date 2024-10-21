package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.PlanAndChargeRequestDTO;
import com.luna.luna_project.dtos.SubscriptionDTO;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.services.PlanService;
import com.luna.luna_project.services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;
    @Autowired
    private ChargeService chargeService;

    @PostMapping("/create-plan-and-charge")
    public ResponseEntity<SubscriptionDTO> createPlanAndCharge(@RequestBody PlanAndChargeRequestDTO request) {
        Plan planSaved = planService.savePlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(chargeService.saveCharge(request, planSaved));
    }

}
