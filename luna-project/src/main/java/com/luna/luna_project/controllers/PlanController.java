package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.CpfDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.OneStepLinkDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.services.ChargeService;
import com.luna.luna_project.services.OneStepService;
import com.luna.luna_project.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
                                                              @RequestParam String cpf) {
        OneStepDTO oneStepSaved = oneStepService.saveOneStep(request, cpf);
        OneStepLinkDTO oneSaved = oneStepService.saveOneStepLink(oneStepSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(oneSaved);
    }

    @GetMapping("/search-clients-by-plan")
    public ResponseEntity<Long> searchClientsByPlan(@RequestParam String name) {
        Long count = planService.searchClientsByPlan(name);
        return ResponseEntity.ok().body(count);
    }

    @DeleteMapping
    public ResponseEntity<String> cancelPlan(@RequestBody CpfDTO cpfDTO) {
        return ResponseEntity.ok().body(planService.cancelPlan(cpfDTO));
    }

    @GetMapping("/count-by-plans")
    public ResponseEntity<Long> countPlans(){
        return ResponseEntity.ok().body(planService.countPlan());
    }



}
