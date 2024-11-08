package com.luna.luna_project.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDay;
    private String cpf;
    @OneToOne
    @JoinColumn(name = "address_id_address", nullable = false)
    private Address address;
}
