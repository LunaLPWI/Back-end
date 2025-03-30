package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

Boolean existsByName(String name);


    boolean existsByCnpj(String cnpj);

}
