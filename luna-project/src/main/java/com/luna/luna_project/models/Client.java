package com.luna.luna_project.models;

import com.luna.luna_project.enums.Plans;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    private String cpf;
    private String email;
    private String password;
    private LocalDate birthDay;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "address_id_address", nullable = false)
    private Address address;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "plan_id_plan", nullable = true)
    private Plan plan;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        // Retorna a senha criptografada
        return password;
    }

    @Override
    public String getUsername() {
        // Retorna o nome de usuário (email ou outro campo)
        return email;
    }

}
