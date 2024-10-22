package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByCpf(String cpf);

    Client findByCpf(String cpf);

    Client deleteByCpf(String cpf);

    Boolean existsByEmail(String email);

    Client findByEmail(String email);

    Client findByEmailAndPassword(String email, String senha);
}
