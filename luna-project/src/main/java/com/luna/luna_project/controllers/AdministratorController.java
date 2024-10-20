package com.luna.luna_project.controllers;


import com.luna.luna_project.dtos.AdminLoginDTO;
import com.luna.luna_project.dtos.AdminTokenDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.gerencianet.subscription.json.ListPlan;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.services.AdminService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

@RestController
@RequestMapping("/adms")
public class AdministratorController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/login")
    public ResponseEntity<AdminTokenDTO> login(@RequestBody AdminLoginDTO adminLoginDTO) throws AccessDeniedException {

        AdminTokenDTO adminLogin = this.adminService.authenticate(adminLoginDTO);

        return ResponseEntity.status(200).body(adminLogin);
    }
}
