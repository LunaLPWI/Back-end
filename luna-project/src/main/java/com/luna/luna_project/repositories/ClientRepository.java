package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByCpf(String cpf);

    Optional <Client> findByCpf(String cpf);

    Client deleteByCpf(String cpf);

    Boolean existsByEmail(String email);

    Optional<Client>  findByEmail(String email);

    Client findByName(String name);

    boolean existsById(Long id);

    List<Client> findByRolesContaining(String role);


    Optional <Client> findByEmailAndPassword(String email, String senha);
}
