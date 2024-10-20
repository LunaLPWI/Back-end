package com.luna.luna_project.gerencianet.subscription.json;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.luna.luna_project.gerencianet.Credentials;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ListPlan {

	public JSONObject listPlan(String name){

		Credentials credentials = new Credentials();

		JSONObject options = new JSONObject();
		options.put("Client_Id_3e8285f22660018a7e7e6e3b5b18c329e35782dd", credentials.getClientId());
		options.put("Client_Id_3e8285f22660018a7e7e6e3b5b18c329e35782dd", credentials.getClientSecret());
		options.put("sandbox", credentials.isSandbox());

		/* ************************************************* */

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("limit", "20");
		params.put("offset", "0");

		try {
            EfiPay efi = new EfiPay(options);
            return efi.call("listPlans", params, new JSONObject());
        } catch (Exception e) {
            return null;
        }
	}
}
