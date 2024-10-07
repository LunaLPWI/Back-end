package com.luna.luna_project.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssinaturaRequest {
    private String back_url;
    private String reason;
    private AutoRecurring auto_recurring;
    private String payer_email;
    private String card_token_id;
    private String status = "authorized";

    @Data
    @Builder
    public static class AutoRecurring {
        private int frequency;
        private String frequency_type;
        private String start_date;
        private String end_date;
        private double transaction_amount;
        private String currency_id;
    }
}

