package com.luna.luna_project.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductScheduling {
    private Long id;
    private String productName;
    private int amount;

}
