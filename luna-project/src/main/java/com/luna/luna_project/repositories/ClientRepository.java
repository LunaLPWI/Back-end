package com.luna.luna_project.repositories;

import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByCpf(String cpf);

    Client findByCpf(String cpf);


    Optional<Client> findByEmail(String email);
}
