package com.luna.luna_project.services;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Office;
import com.luna.luna_project.repositories.ClientRepository;
import com.luna.luna_project.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OfficeService {
    @Autowired
    OfficeRepository officeRepository;


    public Office registerOffice(Office office) {
        return officeRepository.save(office);
    }

    public Office editOfficeValue(double newValue, Long officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        office.setValue(newValue);
        return officeRepository.save(office);
    }

    public Office editOfficeDescription(String newDescription, Long officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        office.setDescription(newDescription);
        return officeRepository.save(office);
    }

    public Office editOfficeDuration(int newDuration, Long officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        office.setDuration(newDuration);
        return officeRepository.save(office);
    }

    public void deleteOffice(Long officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        officeRepository.delete(office);
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public Office getOfficeById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
    }

}
