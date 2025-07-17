package com.nicoletti.rinharouter.model.dto;

public record PaymentRequestDTO(
        String correlationId,
        double amount
) {
}
