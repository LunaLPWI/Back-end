package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.models.OneStepCardSubscription;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-19T21:44:54-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class OneStepCardMapperImpl implements OneStepCardMapper {

    @Override
    public OneStepDTO oneSetToOneStepDTO(OneStepCardSubscription oneStepRequestDTO) {
        if ( oneStepRequestDTO == null ) {
            return null;
        }

        OneStepDTO.OneStepDTOBuilder oneStepDTO = OneStepDTO.builder();

        oneStepDTO.id( oneStepRequestDTO.getId() );
        oneStepDTO.subscription_id( oneStepRequestDTO.getSubscription_id() );
        oneStepDTO.total( oneStepRequestDTO.getTotal() );
        oneStepDTO.payment( oneStepRequestDTO.getPayment() );
        oneStepDTO.first_execution( oneStepRequestDTO.getFirst_execution() );
        oneStepDTO.status( oneStepRequestDTO.getStatus() );
        oneStepDTO.idEstablish( oneStepRequestDTO.getIdEstablish() );

        return oneStepDTO.build();
    }

    @Override
    public OneStepCardSubscription oneStepDTOtoOneStep(OneStepDTO oneStepDTO) {
        if ( oneStepDTO == null ) {
            return null;
        }

        OneStepCardSubscription oneStepCardSubscription = new OneStepCardSubscription();

        oneStepCardSubscription.setIdEstablish( oneStepDTO.getIdEstablish() );
        oneStepCardSubscription.setId( oneStepDTO.getId() );
        oneStepCardSubscription.setSubscription_id( oneStepDTO.getSubscription_id() );
        oneStepCardSubscription.setTotal( oneStepDTO.getTotal() );
        oneStepCardSubscription.setPayment( oneStepDTO.getPayment() );
        oneStepCardSubscription.setFirst_execution( oneStepDTO.getFirst_execution() );
        oneStepCardSubscription.setStatus( oneStepDTO.getStatus() );

        return oneStepCardSubscription;
    }
}
