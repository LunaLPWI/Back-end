package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.ChargeDTO;
import com.luna.luna_project.models.Charge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ChargeMapper {

    ChargeMapper INSTANCE = Mappers.getMapper(ChargeMapper.class);

    ChargeDTO chargeToChargeDTO(Charge charge);

    Charge chargeDTOToCharge(ChargeDTO chargeDTO);

}
