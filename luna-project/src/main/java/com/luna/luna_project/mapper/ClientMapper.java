package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.client.ClientDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client clientDTOtoClient(ClientDTO clientDTO);
    ClientDTO clientToClientDTO(Client client);
    ClientTokenDTO clientToClientDTO(Client client, String token);
//    @mapping(source = "address.cidade", target = "address.cidade")
    Client  clientRequestDTOtoClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO clientToClientDTOResponse(Client client);
    ClientRequestDTO clientToClientDTORequest (Client client);
}
