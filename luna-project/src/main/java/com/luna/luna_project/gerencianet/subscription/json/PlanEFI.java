package com.luna.luna_project.gerencianet.subscription.json;

import br.com.efi.efisdk.EfiPay;
import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.dtos.PlanDTO;
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


	public static Plan createPlan(PlanDTO planDTO) {
		Credentials credentials = new Credentials();

		HashMap<String, Object> options = new HashMap<>();
		options.put("client_id", credentials.getClientId());
		options.put("client_secret", credentials.getClientSecret());
		options.put("sandbox", credentials.isSandbox());

		Map<String, Object> body = new HashMap<>();
		body.put("name", planDTO.getName());
		body.put("interval", planDTO.getInterval());
		body.put("repeats", planDTO.getRepeats());

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


	public static Subscription createCharge(ChargeRequestDTO chargeRequestDTO, Plan plan) {
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


	public static OneStepCardSubscription createOneStep(Plan plan,
														String token,
														ChargeRequestDTO chargeRequestDTO,
														Client client) {
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
		billingAddress.put("street", client.getAddress().getLogradouro());
		billingAddress.put("number", client.getAddress().getNumber());
		billingAddress.put("neighborhood", client.getAddress().getBairro());
		billingAddress.put("zipcode", "03206010");
		billingAddress.put("city", client.getAddress().getCidade());
		billingAddress.put("state", client.getAddress().getUf());

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


	public static OneStepLink createOneStepLink(ChargeRequestDTO chargeRequestDTO){

		Credentials credentials = new Credentials();
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("client_id", credentials.getClientId());
		options.put("client_secret", credentials.getClientSecret());
		options.put("sandbox", credentials.isSandbox());

		/* ************************************************* */

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", "12626");

		List<Object> items = new ArrayList<Object>();
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("name", chargeRequestDTO.getName());
		item1.put("amount", chargeRequestDTO.getAmount());
		item1.put("value", chargeRequestDTO.getValue());
		items.add(item1);

		Map<String, Object> seetings = new HashMap<String, Object>();
		seetings.put("billet_discount", 1);
		seetings.put("card_discount", 1);
		seetings.put("message", "link test");

		LocalDate nextMonth = LocalDate.now().plusMonths(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String expireAt = nextMonth.format(formatter);

		seetings.put("expire_at", expireAt);
		seetings.put("request_delivery_address", false);
		seetings.put("payment_method", "all");

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
}
