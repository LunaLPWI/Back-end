package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.SubscriptionDTO;
import com.luna.luna_project.models.Charge;
import com.luna.luna_project.models.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription);
    Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    default Subscription mapToSub(Map<String, Object> data) {
        Subscription subscription = new Subscription();

        if (data != null) {
            // Safely handle the subscription_id
            Object subscriptionIdObj = data.get("subscription_id");
            if (subscriptionIdObj instanceof String) {
                subscription.setSubscription_id((String) subscriptionIdObj);
            } else if (subscriptionIdObj instanceof Number) {
                subscription.setSubscription_id(String.valueOf(subscriptionIdObj));
            }

            // Safely handle the custom_id
            Object customIdObj = data.get("custom_id");
            if (customIdObj instanceof String) {
                subscription.setCustom_id((String) customIdObj);
            } else if (customIdObj instanceof Number) {
                subscription.setCustom_id(String.valueOf(customIdObj));
            }

            // Handle created_at with custom formatter
            String createdAtStr = (String) data.get("created_at");
            if (createdAtStr != null) {
                subscription.setCreated_at(createdAtStr);
            }

            // Handle status
            subscription.setStatus((String) data.get("status"));

            // Handle charges mapping
            List<Charge> charges = new ArrayList<>();
            List<Map<String, Object>> chargeDataList = (List<Map<String, Object>>) data.get("charges");
            if (chargeDataList != null) {
                for (Map<String, Object> chargeData : chargeDataList) {
                    Charge charge = new Charge();

                    // Safely handle total
                    charge.setTotal(((Number) chargeData.get("total")).intValue());

                    // Safely handle parcel
                    charge.setParcel(((Number) chargeData.get("parcel")).intValue());

                    // Handle charge_id with proper type checks
                    Object chargeIdObj = chargeData.get("charge_id");
                    if (chargeIdObj instanceof String) {
                        charge.setCharge_id((String) chargeIdObj);
                    } else if (chargeIdObj instanceof Number) {
                        charge.setCharge_id(String.valueOf(chargeIdObj));
                    }

                    // Handle status
                    charge.setStatus((String) chargeData.get("status"));
                    charges.add(charge);
                }
            }
        }

        return subscription;
    }
}
