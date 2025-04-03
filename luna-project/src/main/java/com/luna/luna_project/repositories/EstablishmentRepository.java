package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

Boolean existsByName(String name);


    boolean existsByCnpj(String cnpj);

    @Query(value = """
    SELECT e.*, 
           (6371 * ACOS(
               COS(RADIANS(:lat)) * COS(RADIANS(e.lat)) * 
               COS(RADIANS(e.lng) - RADIANS(:lng)) + 
               SIN(RADIANS(:lat)) * SIN(RADIANS(e.lat))
           )) AS distancia
    FROM establishment e
    HAVING distancia <= :raio
    ORDER BY distancia ASC
""", nativeQuery = true)
    List<Establishment> findEstablishmentsByLocationNative(
            @Param("lat") double latitude,
            @Param("lng") double longitude,
            @Param("raio") double raio
    );

}
