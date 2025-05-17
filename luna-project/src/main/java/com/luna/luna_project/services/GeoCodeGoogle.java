package com.luna.luna_project.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.luna.luna_project.models.AddressCoord;
import com.luna.luna_project.dtos.AddressDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeoCodeGoogle {

//    @Value("${google.maps.api.key}")
    private String apiKey = "AIzaSyCQZ8jZZqUo_AGkUvfP9Zx4sSlrVhD5jl8";


    private static final String GOOGLE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
    private static final String GOOGLE_REVERSE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s";




    public AddressCoord getCoordenadas(String endereco) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API Key do Google Maps não foi carregada!");
        }

        String encodedAddress = URLEncoder.encode(endereco, StandardCharsets.UTF_8);
        String urlString = String.format(GOOGLE_GEOCODING_URL, encodedAddress, apiKey);

        JsonObject jsonResponse = getJsonResponse(urlString);

        if (!jsonResponse.get("status").getAsString().equals("OK")) {
            throw new Exception("Erro na API do Google: " + jsonResponse.get("status").getAsString());
        }

        JsonObject location = jsonResponse.getAsJsonArray("results").get(0)
                .getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");
        double lat = location.get("lat").getAsDouble();
        double lng = location.get("lng").getAsDouble();
        return new AddressCoord(lat, lng);
    }

    public AddressDTO getEnderecoFromCoordenadas(double latitude, double longitude) throws Exception {
        String urlString = String.format(GOOGLE_REVERSE_GEOCODING_URL, latitude, longitude, apiKey);
        JsonObject jsonResponse = getJsonResponse(urlString);

        if (!jsonResponse.get("status").getAsString().equals("OK")) {
            throw new Exception("Erro na API do Google: " + jsonResponse.get("status").getAsString());
        }

        JsonArray components = jsonResponse.getAsJsonArray("results").get(0)
                .getAsJsonObject().getAsJsonArray("address_components");

        String logradouro = "", bairro = "", cidade = "", estado = "", cep = "";
        int number = 0;

        for (JsonElement element : components) {
            JsonObject component = element.getAsJsonObject();
            JsonArray types = component.getAsJsonArray("types");

            for (JsonElement type : types) {
                switch (type.getAsString()) {
                    case "street_number":
                        number = Integer.parseInt(component.get("long_name").getAsString());
                        break;
                    case "route":
                        logradouro = component.get("long_name").getAsString();
                        break;
                    case "neighborhood":
                        bairro = component.get("long_name").getAsString();
                        break;
                    case "locality":
                        cidade = component.get("long_name").getAsString();
                        break;
                    case "administrative_area_level_1":
                        estado = component.get("short_name").getAsString();
                        break;
                    case "postal_code":
                        cep = component.get("long_name").getAsString();
                        break;
                }
            }
        }

        return new AddressDTO(cep, logradouro, number, "", cidade, bairro, estado);

    }

    private JsonObject getJsonResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Erro ao conectar à API do Google: Código " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return JsonParser.parseString(response.toString()).getAsJsonObject();
    }

}
