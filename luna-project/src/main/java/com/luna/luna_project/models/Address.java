package com.luna.luna_project.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address_seq")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cep;
    private String logradouro;
    private Integer number;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String cidade;
}
