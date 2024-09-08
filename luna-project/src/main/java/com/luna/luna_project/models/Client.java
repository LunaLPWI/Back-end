package com.luna.luna_project.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    @OneToOne
    @JoinColumn(name = "address_id_address", nullable = false)
    private Address address;
}
