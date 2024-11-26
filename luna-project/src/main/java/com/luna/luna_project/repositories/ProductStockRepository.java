package com.luna.luna_project.repositories;


import com.luna.luna_project.models.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
