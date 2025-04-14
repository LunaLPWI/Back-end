package com.luna.luna_project.controllers;

import com.luna.luna_project.models.Office;
import com.luna.luna_project.services.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    // Criar um novo Office
    @PostMapping
    public ResponseEntity<Office> createOffice(@RequestBody Office office) {
        return ResponseEntity.ok(officeService.registerOffice(office));
    }

    // Editar valor
    @PutMapping("/{id}/value")
    public ResponseEntity<Office> updateValue(@PathVariable Long id, @RequestParam double value) {
        return ResponseEntity.ok(officeService.editOfficeValue(value, id));
    }

    // Editar descrição
    @PutMapping("/{id}/description")
    public ResponseEntity<Office> updateDescription(@PathVariable Long id, @RequestParam String description) {
        return ResponseEntity.ok(officeService.editOfficeDescription(description, id));
    }

    // Editar duração
    @PutMapping("/{id}/duration")
    public ResponseEntity<Office> updateDuration(@PathVariable Long id, @RequestParam int duration) {
        return ResponseEntity.ok(officeService.editOfficeDuration(duration, id));
    }

    // Deletar Office
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.ok("Office deleted successfully");
    }

    // (Opcional) Buscar todos os Offices - útil para testes
    @GetMapping
    public ResponseEntity<List<Office>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    // (Opcional) Buscar Office por ID
    @GetMapping("/{id}")
    public ResponseEntity<Office> getOfficeById(@PathVariable Long id) {
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }
}
