package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.models.Client;

public class ClientMapper {
    public static Client clientRequestDTOtoClient (ClientRequestDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .cpf(clientDTO.getCpf())
                .name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .phone(clientDTO.getPhone())
                .password(clientDTO.getPassword())
                .build();
    }
    public static ClientRequestDTO clientToClientDTORequest (Client client) {
        return ClientRequestDTO.builder()
                .id(client.getId())
                .cpf(client.getCpf())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .password(client.getPassword())
                .build();
    }
    public static ClientResponseDTO clientToClientDTOResponse (Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .cep(client.getAddress().getCep())
                .build();
    }





}
