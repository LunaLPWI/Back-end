package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.addresses.AddressDTO;
import com.luna.luna_project.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "cidade", target = "cidade")
    Address addressDTOtoAddress(AddressDTO addressDTO);
    AddressDTO addressToAddressDTO(Address address);

}
