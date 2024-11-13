package com.luna.luna_project.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer amount;
    private String description;
    private Double price;
}
