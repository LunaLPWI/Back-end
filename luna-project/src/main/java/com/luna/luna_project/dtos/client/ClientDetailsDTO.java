package com.luna.luna_project.dtos.client;

import com.luna.luna_project.models.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDetailsDTO implements UserDetails {

    private final String name;
    private final String email;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public ClientDetailsDTO(Client client) {
        this.name = client.getName();
        this.email = client.getEmail();
        this.password = client.getPassword();

        this.authorities = client.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toSet());
    }

    public String getNome() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
