package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.AdminLoginDTO;
import com.luna.luna_project.dtos.AdminTokenDTO;
import com.luna.luna_project.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

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
