package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
