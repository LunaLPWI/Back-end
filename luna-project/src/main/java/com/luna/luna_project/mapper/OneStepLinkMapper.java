package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.OneStepLinkDTO;
import com.luna.luna_project.models.Charge;
import com.luna.luna_project.models.OneStepCardSubscription;
import com.luna.luna_project.models.OneStepLink;
import com.luna.luna_project.models.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface OneStepLinkMapper {

    OneStepLinkMapper INSTANCE = Mappers.getMapper(OneStepLinkMapper.class);

    OneStepLinkDTO oneSetToOneStepDTO(OneStepLink oneStepRequestDTO);

    OneStepLink oneStepDTOtoOneStep(OneStepLinkDTO oneStepDTO);


    default OneStepLink mapToOneStepLink(Map<String, Object> data) {
        OneStepLink oneStepLink = new OneStepLink();

        if (data != null) {
            // Handle card_discount
            Object cardDiscountObj = data.get("card_discount");
            if (cardDiscountObj instanceof Number) {
                oneStepLink.setCard_discount(((Number) cardDiscountObj).intValue());
            }

            // Handle payment_url
            oneStepLink.setPayment_url((String) data.get("payment_url"));

            // Handle charges as a list
            Object chargesObj = data.get("charges");
            if (chargesObj instanceof List) {
                List<Map<String, Object>> chargesList = (List<Map<String, Object>>) chargesObj;
                List<Charge> charges = new ArrayList<>();

                for (Map<String, Object> chargeData : chargesList) {
                    Charge charge = new Charge();

                    // Handle charge_id
                    Object chargeIdObj = chargeData.get("id");
                    if (chargeIdObj != null) {
                        charge.setCharge_id(String.valueOf(chargeIdObj));
                    }

                    // Safely handle parcel and total within charge
                    Object parcelObj = chargeData.get("parcel");
                    if (parcelObj instanceof Number) {
                        charge.setParcel(((Number) parcelObj).intValue());
                    }

                    Object chargeTotalObj = chargeData.get("total");
                    if (chargeTotalObj instanceof Number) {
                        charge.setTotal(((Number) chargeTotalObj).intValue());
                    }

                    // Handle status
                    charge.setStatus((String) chargeData.get("status"));

                    charges.add(charge);
                }

                // Set the list of charges in subscription
                oneStepLink.setCharges(charges);
            }

            // Handle custom_id
            oneStepLink.setCustom_id((String) data.get("custom_id"));

            // Handle created_at
            oneStepLink.setCreated_at((String) data.get("created_at"));

            // Handle message
            oneStepLink.setMessage((String) data.get("message"));

            // Handle subscription_id
            Object subscriptionIdObj = data.get("subscription_id");
            if (subscriptionIdObj instanceof Number) {
                oneStepLink.setSubscription_id(((Number) subscriptionIdObj).intValue());
            }

            // Handle billet_discount
            Object billetDiscountObj = data.get("billet_discount");
            if (billetDiscountObj instanceof Number) {
                oneStepLink.setBillet_discount(((Number) billetDiscountObj).intValue());
            }

            // Handle conditional_discount_date
            oneStepLink.setConditional_discount_date((String) data.get("conditional_discount_date"));

            // Handle expire_at
            oneStepLink.setExpire_at((String) data.get("expire_at"));

            // Handle request_delivery_address
            oneStepLink.setRequest_delivery_address((Boolean) data.get("request_delivery_address"));

            // Handle payment_method
            oneStepLink.setPayment_method((String) data.get("payment_method"));

            // Handle status
            oneStepLink.setStatus((String) data.get("status"));
        }

        return oneStepLink;
    }

}
