package com.luna.luna_project.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{11})$", message = "Número de telefone deve ter 10 ou 11 dígitos")
    private String cellphone;
    private String password;
    @ManyToOne
    @JoinColumn(name = "address_id_address", nullable = false)
    private Address address;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();


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

    @Override
    public boolean isAccountNonExpired() {
        // Retorna se a conta está expirada (exemplo simples)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Retorna se a conta está bloqueada (exemplo simples)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Retorna se as credenciais (senha) expiraram (exemplo simples)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Retorna se a conta está habilitada (exemplo simples)
        return true;
    }
}
