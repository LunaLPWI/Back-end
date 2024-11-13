package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.OneStepLinkDTO;
import com.luna.luna_project.services.ChargeService;
import com.luna.luna_project.services.OneStepService;
import com.luna.luna_project.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private OneStepService oneStepService;

    @PostMapping("/create-plan-and-charge")
    public ResponseEntity<OneStepLinkDTO> createPlanAndCharge(@RequestBody OneStepDTO request,
                                                              @RequestParam String paymentToken,
                                                              @RequestParam String cpf) {
        OneStepDTO oneStepSaved = oneStepService.saveOneStep(request, paymentToken, cpf);
        OneStepLinkDTO oneSaved = oneStepService.saveOneStepLink(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(oneSaved);
    }

}
