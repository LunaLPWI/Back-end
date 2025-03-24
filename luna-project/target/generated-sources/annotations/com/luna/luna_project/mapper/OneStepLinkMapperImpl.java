package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.OneStepLinkDTO;
import com.luna.luna_project.models.OneStepLink;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:53-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class OneStepLinkMapperImpl implements OneStepLinkMapper {

    @Override
    public OneStepLinkDTO oneSetToOneStepDTO(OneStepLink oneStepRequestDTO) {
        if ( oneStepRequestDTO == null ) {
            return null;
        }

        OneStepLinkDTO oneStepLinkDTO = new OneStepLinkDTO();

        oneStepLinkDTO.setId( oneStepRequestDTO.getId() );
        oneStepLinkDTO.setPayment_url( oneStepRequestDTO.getPayment_url() );
        oneStepLinkDTO.setCreated_at( oneStepRequestDTO.getCreated_at() );
        oneStepLinkDTO.setSubscription_id( oneStepRequestDTO.getSubscription_id() );
        oneStepLinkDTO.setExpire_at( oneStepRequestDTO.getExpire_at() );
        oneStepLinkDTO.setRequest_delivery_address( oneStepRequestDTO.getRequest_delivery_address() );
        oneStepLinkDTO.setPayment_method( oneStepRequestDTO.getPayment_method() );
        oneStepLinkDTO.setStatus( oneStepRequestDTO.getStatus() );

        return oneStepLinkDTO;
    }

    @Override
    public OneStepLink oneStepDTOtoOneStep(OneStepLinkDTO oneStepDTO) {
        if ( oneStepDTO == null ) {
            return null;
        }

        OneStepLink oneStepLink = new OneStepLink();

        oneStepLink.setId( oneStepDTO.getId() );
        oneStepLink.setPayment_url( oneStepDTO.getPayment_url() );
        oneStepLink.setCreated_at( oneStepDTO.getCreated_at() );
        oneStepLink.setSubscription_id( oneStepDTO.getSubscription_id() );
        oneStepLink.setExpire_at( oneStepDTO.getExpire_at() );
        oneStepLink.setRequest_delivery_address( oneStepDTO.getRequest_delivery_address() );
        oneStepLink.setPayment_method( oneStepDTO.getPayment_method() );
        oneStepLink.setStatus( oneStepDTO.getStatus() );

        return oneStepLink;
    }
}
