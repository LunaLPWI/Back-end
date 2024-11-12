package com.luna.luna_project.repositories;


import com.luna.luna_project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
