package com.luna.luna_project.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    String nome;
    @Min(0)
    @NotNull
    private Integer qtd;
    @NotBlank
    private String descricao;
    @Min(0)
    @NotNull
    private Double price;
}
