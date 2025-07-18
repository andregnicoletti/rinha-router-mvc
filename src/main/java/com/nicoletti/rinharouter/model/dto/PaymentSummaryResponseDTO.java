package com.nicoletti.rinharouter.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentSummaryResponseDTO(
        @JsonProperty("default")
        PaymentResultsDTO defaultResults,
        @JsonProperty("fallback")
        PaymentResultsDTO fallbackResults
) {
}
