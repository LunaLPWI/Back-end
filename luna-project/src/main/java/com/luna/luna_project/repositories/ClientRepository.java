package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    @Query("SELECT COUNT(DISTINCT s.client.id) FROM Scheduling s WHERE s.startDateTime BETWEEN :startDate AND :endDate GROUP BY s.client.id HAVING COUNT(s) < 5")
    long countOccasionalClients(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT c FROM Client c JOIN c.establishments e WHERE e.id = :establishmentId AND :role MEMBER OF c.roles")
    List<Client> findEmployeesByEstablishmentIdAndRole(@Param("establishmentId") Long establishmentId, @Param("role") String role);

    @Query("SELECT COUNT(DISTINCT s.client.id) FROM Scheduling s " +
            "WHERE s.startDateTime BETWEEN :startDate AND :endDate " +
            "AND s.employee.id IN (SELECT e.id FROM Client e JOIN e.establishments est " +
            "WHERE est.id = :establishmentId AND :role MEMBER OF e.roles) " +
            "GROUP BY s.client.id HAVING COUNT(s) < :frequencyLevel")
    Long getFrequencyClientsClientsByEstablishmentAndRole(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("establishmentId") Long establishmentId,
                                                      @Param("role") String role,
                                                      @Param("frequencyLevel")Integer frequencyLevel);

}
