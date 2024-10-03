package com.luna.luna_project.services;


import com.luna.luna_project.models.AssinaturaRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssinaturaService {

    private final String accessToken = "TEST-6830565142196449-092815-08f714c3f2a1f2fe49bcf53637726e75-1761296678";


    private final String url = "https://api.mercadopago.com/preapproval";

    public String criarAssinatura(AssinaturaRequest request) {

        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<AssinaturaRequest> entity = new HttpEntity<>(request, headers);


        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}


//axios.post('/mercado-pago', {
//    id: "123"
//})
//
//
//if (nãoteminfor) {
//        axios.post('/mercado-pago', {
//    id: "123"
//})
//        }
//
//
//client > post
//api > get depois de gravar os dados
//api > retorna a response entity no POST que o client solicitou, o get foi feito debaixo dos panos
