package com.luna.luna_project.gerencianet;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Getter
public class Credentials {
	private String clientId;
	private String clientSecret;
	private String certificate;
	private boolean sandbox;
	private boolean debug;

	public Credentials() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream credentialsFile = classLoader.getResourceAsStream("credentials.json");

		if (credentialsFile == null) {
			throw new RuntimeException("credentials.json file not found");
		}

		// Parse o arquivo JSON
		try {
			JSONParser parser = new JSONParser();
			JSONObject credentials = (JSONObject) parser.parse(new InputStreamReader(credentialsFile, StandardCharsets.UTF_8));

			// Fechar o arquivo após leitura
			credentialsFile.close();

			// Atribuir os valores das credenciais
			this.clientId = (String) credentials.get("client_id");
			this.clientSecret = (String) credentials.get("client_secret");
			this.certificate = (String) credentials.get("certificate");
			this.sandbox = (boolean) credentials.get("sandbox");
			this.debug = (boolean) credentials.get("debug");
		} catch (IOException | ParseException e) {
			System.out.println("Error reading or parsing the credentials file: " + e.getMessage());
		}
	}
}
