package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.ClientDTO;
import com.luna.luna_project.models.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client clientDTOtoClient(ClientDTO clientDTO);
    ClientDTO clientToClientDTO(Client client);
}
