package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN c.establishments e WHERE e.id = :establishmentId AND 'ROLE_EMPLOYEE' IN elements(c.roles)")
    List<Client> findEmployeesByEstablishmentId(@Param("establishmentId") Long establishmentId);
    Boolean existsByCpf(String cpf);

    Optional <Client> findByCpf(String cpf);

    Client deleteByCpf(String cpf);

    Boolean existsByEmail(String email);

    Optional<Client>  findByEmail(String email);

    Client findByName(String name);

    boolean existsById(Long id);

    List<Client> findByRolesContaining(String role);


    Optional <Client> findByEmailAndPassword(String email, String senha);

    Optional<Client> findClientById(Long idClient);



}
