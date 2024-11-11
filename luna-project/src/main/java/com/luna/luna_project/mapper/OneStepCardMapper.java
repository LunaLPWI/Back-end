package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.models.Charge;
import com.luna.luna_project.models.OneStepCardSubscription;
import com.luna.luna_project.models.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Mapper(componentModel = "spring")
public interface OneStepCardMapper {

    OneStepCardMapper INSTANCE = Mappers.getMapper(OneStepCardMapper.class);

    OneStepDTO oneSetToOneStepDTO(OneStepCardSubscription oneStepRequestDTO);

    OneStepCardSubscription oneStepDTOtoOneStep(OneStepDTO oneStepDTO);

    default OneStepCardSubscription mapToOneStepCardSub(Map<String, Object> data) {
        OneStepCardSubscription subscription = new OneStepCardSubscription();

        if (data != null) {
            // Handle subscription_id
            Object subscriptionIdObj = data.get("subscription_id");
            if (subscriptionIdObj != null) {
                subscription.setSubscription_id(String.valueOf(subscriptionIdObj));
            }

            // Handle total
            Object totalObj = data.get("total");
            if (totalObj instanceof Number) {
                subscription.setTotal(((Number) totalObj).intValue());
            }

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
                subscription.setCharges(charges);
            }

            // Handle payment type
            subscription.setPayment((String) data.get("payment"));

            // Handle first_execution with custom formatter (dd/MM/yyyy format)
            String firstExecutionStr = (String) data.get("first_execution");
            if (firstExecutionStr != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(firstExecutionStr, formatter);
                subscription.setFirst_execution(date.atStartOfDay()); // Define como LocalDateTime no início do dia
            }


            // Handle plan details as a list
            Object plansObj = data.get("plans");
            if (plansObj instanceof List) {
                List<Map<String, Object>> plansList = (List<Map<String, Object>>) plansObj;
                List<Plan> plans = new ArrayList<>();

                for (Map<String, Object> planData : plansList) {
                    Plan plan = new Plan();

                    Object repeatsObj = planData.get("repeats");
                    if (repeatsObj instanceof Number) {
                        plan.setRepeats(((Number) repeatsObj).intValue());
                    }

                    Object intervalObj = planData.get("interval");
                    if (intervalObj instanceof Number) {
                        plan.setInterval(((Number) intervalObj).intValue());
                    }

                    Object planIdObj = planData.get("id");
                    if (planIdObj != null) {
                        plan.setPlan_id(String.valueOf(planIdObj));
                    }

                    plans.add(plan);
                }

                // Set the list of plans in subscription
                subscription.setPlans(plans);
            }

            // Handle status
            subscription.setStatus((String) data.get("status"));
        }

        return subscription;
    }




}
