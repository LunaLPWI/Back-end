package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.dtos.client.ClientDTO;
import com.luna.luna_project.dtos.client.ClientRequestDTO;
import com.luna.luna_project.dtos.client.ClientResponseDTO;
import com.luna.luna_project.dtos.client.ClientTokenDTO;
import com.luna.luna_project.models.Address;
import com.luna.luna_project.models.Client;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:52-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client clientDTOtoClient(ClientDTO clientDTO) {
        if ( clientDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.id( clientDTO.id() );
        client.name( clientDTO.name() );
        client.cpf( clientDTO.cpf() );
        client.email( clientDTO.email() );
        client.password( clientDTO.password() );
        client.birthDay( clientDTO.birthDay() );
        client.phoneNumber( clientDTO.phoneNumber() );
        client.address( addressDTOToAddress( clientDTO.address() ) );
        Set<String> set = clientDTO.roles();
        if ( set != null ) {
            client.roles( new LinkedHashSet<String>( set ) );
        }

        return client.build();
    }

    @Override
    public ClientDTO clientToClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String password = null;
        String phoneNumber = null;
        String cpf = null;
        LocalDate birthDay = null;
        AddressDTO address = null;
        Set<String> roles = null;

        id = client.getId();
        name = client.getName();
        email = client.getEmail();
        password = client.getPassword();
        phoneNumber = client.getPhoneNumber();
        cpf = client.getCpf();
        birthDay = client.getBirthDay();
        address = addressToAddressDTO( client.getAddress() );
        Set<String> set = client.getRoles();
        if ( set != null ) {
            roles = new LinkedHashSet<String>( set );
        }

        ClientDTO clientDTO = new ClientDTO( id, name, email, password, phoneNumber, cpf, birthDay, address, roles );

        return clientDTO;
    }

    @Override
    public ClientTokenDTO clientToClientDTO(Client client, String token) {
        if ( client == null && token == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String cpf = null;
        String email = null;
        Address address = null;
        LocalDate birthDay = null;
        String phoneNumber = null;
        Set<String> roles = null;
        if ( client != null ) {
            id = client.getId();
            name = client.getName();
            cpf = client.getCpf();
            email = client.getEmail();
            address = client.getAddress();
            birthDay = client.getBirthDay();
            phoneNumber = client.getPhoneNumber();
            Set<String> set = client.getRoles();
            if ( set != null ) {
                roles = new LinkedHashSet<String>( set );
            }
        }
        String token1 = null;
        token1 = token;

        ClientTokenDTO clientTokenDTO = new ClientTokenDTO( id, name, cpf, email, token1, address, birthDay, phoneNumber, roles );

        return clientTokenDTO;
    }

    @Override
    public Client clientRequestDTOtoClient(ClientRequestDTO clientRequestDTO) {
        if ( clientRequestDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.address( addressDTOToAddress1( clientRequestDTO.getAddress() ) );
        client.id( clientRequestDTO.getId() );
        client.name( clientRequestDTO.getName() );
        client.cpf( clientRequestDTO.getCpf() );
        client.email( clientRequestDTO.getEmail() );
        client.password( clientRequestDTO.getPassword() );
        client.birthDay( clientRequestDTO.getBirthDay() );
        client.phoneNumber( clientRequestDTO.getPhoneNumber() );
        Set<String> set = clientRequestDTO.getRoles();
        if ( set != null ) {
            client.roles( new LinkedHashSet<String>( set ) );
        }

        return client.build();
    }

    @Override
    public ClientResponseDTO clientToClientDTOResponse(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO.ClientResponseDTOBuilder clientResponseDTO = ClientResponseDTO.builder();

        clientResponseDTO.id( client.getId() );
        clientResponseDTO.name( client.getName() );
        clientResponseDTO.email( client.getEmail() );
        clientResponseDTO.cpf( client.getCpf() );
        clientResponseDTO.phoneNumber( client.getPhoneNumber() );
        clientResponseDTO.birthDay( client.getBirthDay() );
        Set<String> set = client.getRoles();
        if ( set != null ) {
            clientResponseDTO.roles( new LinkedHashSet<String>( set ) );
        }

        return clientResponseDTO.build();
    }

    @Override
    public ClientRequestDTO clientToClientDTORequest(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientRequestDTO.ClientRequestDTOBuilder clientRequestDTO = ClientRequestDTO.builder();

        clientRequestDTO.id( client.getId() );
        clientRequestDTO.name( client.getName() );
        clientRequestDTO.cpf( client.getCpf() );
        clientRequestDTO.email( client.getEmail() );
        clientRequestDTO.phoneNumber( client.getPhoneNumber() );
        clientRequestDTO.password( client.getPassword() );
        clientRequestDTO.address( addressToAddressDTO( client.getAddress() ) );
        clientRequestDTO.birthDay( client.getBirthDay() );
        Set<String> set = client.getRoles();
        if ( set != null ) {
            clientRequestDTO.roles( new LinkedHashSet<String>( set ) );
        }

        return clientRequestDTO.build();
    }

    protected Address addressDTOToAddress(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.cep( addressDTO.getCep() );
        address.logradouro( addressDTO.getLogradouro() );
        address.number( addressDTO.getNumber() );
        address.complemento( addressDTO.getComplemento() );
        address.bairro( addressDTO.getBairro() );
        address.uf( addressDTO.getUf() );
        address.cidade( addressDTO.getCidade() );

        return address.build();
    }

    protected AddressDTO addressToAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO.AddressDTOBuilder addressDTO = AddressDTO.builder();

        addressDTO.cep( address.getCep() );
        addressDTO.logradouro( address.getLogradouro() );
        addressDTO.number( address.getNumber() );
        addressDTO.complemento( address.getComplemento() );
        addressDTO.cidade( address.getCidade() );
        addressDTO.bairro( address.getBairro() );
        addressDTO.uf( address.getUf() );

        return addressDTO.build();
    }

    protected Address addressDTOToAddress1(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.cidade( addressDTO.getCidade() );
        address.cep( addressDTO.getCep() );
        address.logradouro( addressDTO.getLogradouro() );
        address.number( addressDTO.getNumber() );
        address.complemento( addressDTO.getComplemento() );
        address.bairro( addressDTO.getBairro() );
        address.uf( addressDTO.getUf() );

        return address.build();
    }
}
