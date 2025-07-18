package com.nicoletti.rinharouter.model.dto;

public record PaymentProcessorRequestDTO(
        String correlationId,
        double amount,
        String requestedAt
) {
}