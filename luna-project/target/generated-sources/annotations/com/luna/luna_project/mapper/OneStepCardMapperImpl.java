package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.models.OneStepCardSubscription;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:53-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class OneStepCardMapperImpl implements OneStepCardMapper {

    @Override
    public OneStepDTO oneSetToOneStepDTO(OneStepCardSubscription oneStepRequestDTO) {
        if ( oneStepRequestDTO == null ) {
            return null;
        }

        OneStepDTO oneStepDTO = new OneStepDTO();

        oneStepDTO.setId( oneStepRequestDTO.getId() );
        oneStepDTO.setSubscription_id( oneStepRequestDTO.getSubscription_id() );
        oneStepDTO.setTotal( oneStepRequestDTO.getTotal() );
        oneStepDTO.setPayment( oneStepRequestDTO.getPayment() );
        oneStepDTO.setFirst_execution( oneStepRequestDTO.getFirst_execution() );
        oneStepDTO.setStatus( oneStepRequestDTO.getStatus() );

        return oneStepDTO;
    }

    @Override
    public OneStepCardSubscription oneStepDTOtoOneStep(OneStepDTO oneStepDTO) {
        if ( oneStepDTO == null ) {
            return null;
        }

        OneStepCardSubscription oneStepCardSubscription = new OneStepCardSubscription();

        oneStepCardSubscription.setId( oneStepDTO.getId() );
        oneStepCardSubscription.setSubscription_id( oneStepDTO.getSubscription_id() );
        oneStepCardSubscription.setTotal( oneStepDTO.getTotal() );
        oneStepCardSubscription.setPayment( oneStepDTO.getPayment() );
        oneStepCardSubscription.setFirst_execution( oneStepDTO.getFirst_execution() );
        oneStepCardSubscription.setStatus( oneStepDTO.getStatus() );

        return oneStepCardSubscription;
    }
}
