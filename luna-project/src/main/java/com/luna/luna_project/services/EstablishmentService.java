package com.luna.luna_project.services;

import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.repositories.EstablishmentRepository;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    public Establishment saveEstablishment(Establishment establishment) {

        if (!establishmentRepository.existsByName(establishment.getName())) {

        }

        return establishmentRepository.save(establishment);
    }

}
