package com.luna.luna_project.repositories;


import com.luna.luna_project.models.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductStock, Long> {
    List<ProductStock> findByScheduling_Id(Long scheduling_Id);
    ProductStock findByProduct_Id(Long product_Id);
}
