package com.luna.luna_project.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    private static WhatsAppService instance;

    public WhatsAppService() {
        instance = this;
    }

    public static WhatsAppService getInstance() {
        return instance;
    }

    private static final String ACCOUNT_SID = "ACdab3efae1bd9cf71ddf83e269d99dcef";
    private static final String AUTH_TOKEN = "5b4e9500d8115aab2f5f35367c612e60";
    private static final String FROM = "whatsapp:+18777804236";

    public void enviarMensagem(String texto, String numeroDestino) {


        String number = "whatsapp:+"+numeroDestino;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(number),
                new com.twilio.type.PhoneNumber(FROM),
                texto
        ).create();

        System.out.println("Mensagem enviada para " + numeroDestino + ": " + message.getSid());
    }
}

