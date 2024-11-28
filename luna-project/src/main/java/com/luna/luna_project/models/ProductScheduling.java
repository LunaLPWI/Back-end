package com.luna.luna_project.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductScheduling {
    private Long id;
    private String productName;
    private int amount;
    private double price;
}
