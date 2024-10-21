package com.luna.luna_project.gerencianet.subscription.json;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.gerencianet.Credentials;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.mapper.SubscriptionMapper;
import com.luna.luna_project.models.Charge;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.models.Subscription;

import java.time.LocalDateTime;
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
			// Log the exception
			e.printStackTrace();
			return new Subscription(); // Return an empty Subscription instead of null
		}
	}


	public static Subscription createChargeWithPlanId(String planId, ChargeRequestDTO chargeRequestDTO) {
		Credentials credentials = new Credentials();

		HashMap<String, Object> options = new HashMap<>();
		options.put("client_id", credentials.getClientId());
		options.put("client_secret", credentials.getClientSecret());
		options.put("sandbox", credentials.isSandbox());

		HashMap<String, String> params = new HashMap<>();
		params.put("id", planId); // Usa o planId dinâmico

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
			// Log the exception
			e.printStackTrace();
			return new Subscription(); // Retorna um Subscription vazio ao invés de null
		}
	}






}
