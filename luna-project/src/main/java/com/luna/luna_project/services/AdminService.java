package com.luna.luna_project.services;

import com.luna.luna_project.dtos.AdminLoginDTO;
import com.luna.luna_project.dtos.AdminTokenDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.exceptions.AdminAccessDeniedException;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

@Service
public class AdminService {

    @Value("${authenticate.token}")
    private String ADMIN_SECRET_TOKEN;

    public AdminTokenDTO authenticate(AdminLoginDTO adminLoginDTO) {
        if (isValidAdmin(adminLoginDTO)) {
            return new AdminTokenDTO(ADMIN_SECRET_TOKEN);
        } else {
            throw new AdminAccessDeniedException("Credenciais de administrador inválidas");
        }
    }

    private boolean isValidAdmin(AdminLoginDTO adminLoginDTO) {
        String adminEmail = "domroque@gmail.com";
        String adminPassword = "domque@376";
        return adminLoginDTO.getEmail().equals(adminEmail) && adminLoginDTO.getPassword().equals(adminPassword);
    }

}
