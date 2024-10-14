package com.luna.luna_project.controllers;


import com.luna.luna_project.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adms")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;



}
