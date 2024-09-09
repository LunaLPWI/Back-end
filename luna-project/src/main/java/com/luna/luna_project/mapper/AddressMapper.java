package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.AddressDTO;
import com.luna.luna_project.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address addressDTOtoAddress(AddressDTO addressDTO);
    AddressDTO addressToAddressDTO(Address address);

}
