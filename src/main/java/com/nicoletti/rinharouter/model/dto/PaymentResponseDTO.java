package com.nicoletti.rinharouter.model.dto;

public record PaymentResponseDTO(
        boolean status,
        String correlationId,
        double amount
) {
}
