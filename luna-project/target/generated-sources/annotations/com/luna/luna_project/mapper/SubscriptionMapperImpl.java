package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.SubscriptionDTO;
import com.luna.luna_project.models.Subscription;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:52-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class SubscriptionMapperImpl implements SubscriptionMapper {

    @Override
    public SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();

        subscriptionDTO.setId( subscription.getId() );
        subscriptionDTO.setCustom_id( subscription.getCustom_id() );
        subscriptionDTO.setCreated_at( subscription.getCreated_at() );
        subscriptionDTO.setStatus( subscription.getStatus() );

        return subscriptionDTO;
    }

    @Override
    public Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO) {
        if ( subscriptionDTO == null ) {
            return null;
        }

        Subscription subscription = new Subscription();

        subscription.setId( subscriptionDTO.getId() );
        subscription.setCustom_id( subscriptionDTO.getCustom_id() );
        subscription.setCreated_at( subscriptionDTO.getCreated_at() );
        subscription.setStatus( subscriptionDTO.getStatus() );

        return subscription;
    }
}
