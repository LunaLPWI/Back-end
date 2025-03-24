package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.ChargeDTO;
import com.luna.luna_project.models.Charge;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:52-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ChargeMapperImpl implements ChargeMapper {

    @Override
    public ChargeDTO chargeToChargeDTO(Charge charge) {
        if ( charge == null ) {
            return null;
        }

        ChargeDTO chargeDTO = new ChargeDTO();

        chargeDTO.setTotal( charge.getTotal() );
        chargeDTO.setParcel( charge.getParcel() );
        if ( charge.getCharge_id() != null ) {
            chargeDTO.setCharge_id( Long.parseLong( charge.getCharge_id() ) );
        }
        chargeDTO.setStatus( charge.getStatus() );

        return chargeDTO;
    }

    @Override
    public Charge chargeDTOToCharge(ChargeDTO chargeDTO) {
        if ( chargeDTO == null ) {
            return null;
        }

        Charge charge = new Charge();

        charge.setTotal( chargeDTO.getTotal() );
        charge.setParcel( chargeDTO.getParcel() );
        if ( chargeDTO.getCharge_id() != null ) {
            charge.setCharge_id( String.valueOf( chargeDTO.getCharge_id() ) );
        }
        charge.setStatus( chargeDTO.getStatus() );

        return charge;
    }
}
