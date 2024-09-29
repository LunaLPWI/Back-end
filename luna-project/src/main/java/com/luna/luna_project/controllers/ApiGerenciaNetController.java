package com.luna.luna_project.controllers;
import com.luna.luna_project.services.ApiGerenciaNetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiGerenciaNetController {

    private final ApiGerenciaNetService subscriptionService;


    public ApiGerenciaNetController(ApiGerenciaNetService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/create-subscription")
    public String createSubscription() {
        return subscriptionService.createSubscription();
    }
}
