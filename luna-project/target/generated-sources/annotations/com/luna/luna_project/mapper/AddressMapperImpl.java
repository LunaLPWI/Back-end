package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:53-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address addressDTOtoAddress(AddressDTO addressDTO) {
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

    @Override
    public AddressDTO addressToAddressDTO(Address address) {
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
}
