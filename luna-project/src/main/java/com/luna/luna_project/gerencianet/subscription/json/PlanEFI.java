package com.luna.luna_project.gerencianet.subscription.json;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.gerencianet.Credentials;
import com.luna.luna_project.mapper.OneStepCardMapper;
import com.luna.luna_project.mapper.OneStepLinkMapper;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.mapper.SubscriptionMapper;
import com.luna.luna_project.models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanEFI {


    public static Plan createPlan(PlanDTO planDTO, Plans plans) {
        Credentials credentials = new Credentials();

        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());

        Map<String, Object> body = new HashMap<>();
        body.put("name", plans);
        body.put("interval", 1);
        body.put("repeats", null);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("createPlan", new HashMap<>(), body);

            Map<String, Object> data = (Map<String, Object>) response.get("data");
            return PlanMapper.INSTANCE.mapToPlan(data);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Subscription createCharge(Plans chargeRequestDTO, PlanDTO plan) {
        Credentials credentials = new Credentials();

        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());

        HashMap<String, String> params = new HashMap<>();
        params.put("id", plan.getPlan_id());

        List<Object> items = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("name", chargeRequestDTO.getName());
        item1.put("amount", chargeRequestDTO.getAmount());
        item1.put("value", chargeRequestDTO.getValue());
        items.add(item1);

        Map<String, Object> body = new HashMap<>();
        body.put("items", items);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("createSubscription", params, body);

            Map<String, Object> data = (Map<String, Object>) response.get("data");
            return SubscriptionMapper.INSTANCE.mapToSub(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Subscription();
        }
    }


    public static OneStepCardSubscription createOneStep(PlanDTO plan,
                                                        String token,
                                                        Plans chargeRequestDTO,
                                                        Client client, Establishment establishment) {
        Credentials credentials = new Credentials();
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", plan.getPlan_id());

        List<Object> items = new ArrayList<Object>();
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("name", chargeRequestDTO.getName());
        item1.put("amount", chargeRequestDTO.getAmount());
        item1.put("value", chargeRequestDTO.getValue());
        items.add(item1);

        Map<String, Object> customer = new HashMap<String, Object>();
        customer.put("name", client.getName());
        customer.put("cpf", client.getCpf());
        customer.put("phone_number", client.getPhoneNumber());
        customer.put("email", client.getEmail());
        customer.put("birth", client.getBirthDay());

        Map<String, Object> billingAddress = new HashMap<String, Object>();
        billingAddress.put("street", establishment.getAddress().getLogradouro());
        billingAddress.put("number", establishment.getAddress().getNumber());
        billingAddress.put("neighborhood", establishment.getAddress().getBairro());
        billingAddress.put("zipcode", "03206010");
        billingAddress.put("city", establishment.getAddress().getCidade());
        billingAddress.put("state", establishment.getAddress().getUf());

        Map<String, Object> creditCard = new HashMap<String, Object>();
        creditCard.put("installments", null);
        creditCard.put("billing_address", billingAddress);
        creditCard.put("payment_token", token);
        creditCard.put("customer", customer);
        Map<String, Object> payment = new HashMap<String, Object>();
        payment.put("credit_card", creditCard);
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("items", items);
        body.put("payment", payment);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("createOneStepSubscription", params, body);
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            return OneStepCardMapper.INSTANCE.mapToOneStepCardSub(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new OneStepCardSubscription();
        }
    }


    public static OneStepLink createOneStepLink(OneStepDTO request) {

        Credentials credentials = new Credentials();
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());

        /* ************************************************* */

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", request.getPlan().getPlan_id());

        List<Object> items = new ArrayList<Object>();
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("name", request.getChargeRequest().getName());
        item1.put("amount", 1);
        item1.put("value", request.getChargeRequest().getValue());
        items.add(item1);

        Map<String, Object> seetings = new HashMap<String, Object>();

        LocalDate nextMonth = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expireAt = nextMonth.format(formatter);

        seetings.put("expire_at", expireAt);
        seetings.put("request_delivery_address", false);
        seetings.put("payment_method", "credit_card");

        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("notification_url", "http://domain.com/notification");

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("items", items);
        body.put("settings", seetings);
        body.put("metadata", metadata);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("createOneStepSubscriptionLink", params, body);
            Map<String, Object> data = (Map<String, Object>) response.get("data");

            return OneStepLinkMapper.INSTANCE.mapToOneStepLink(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new OneStepLink();
        }
    }


    public static String cancelSubscription(String id) {
        Credentials credentials = new Credentials();

        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());


        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("cancelSubscription", params, new HashMap<>());
            return response.toString();
        } catch (EfiPayException e) {
            return String.format("Error: Code: %s, Error: %s, Description: %s",
                    e.getCode(), e.getError(), e.getErrorDescription());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
