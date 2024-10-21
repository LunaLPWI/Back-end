package com.luna.luna_project.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{11})$", message = "Número de telefone deve ter 10 ou 11 dígitos")
    private String phone;
    private String password;
    @OneToOne
    @JoinColumn(name = "address_id_address", nullable = false)
    private Address address;

}
