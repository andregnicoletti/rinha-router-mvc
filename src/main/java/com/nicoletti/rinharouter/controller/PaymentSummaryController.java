package com.nicoletti.rinharouter.controller;

import com.nicoletti.rinharouter.model.dto.PaymentSummaryResponseDTO;
import com.nicoletti.rinharouter.service.api.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payments-summary")
public class PaymentSummaryController {

    private PaymentService paymentProcessorService;

    public PaymentSummaryController(PaymentService paymentProcessorService) {
        this.paymentProcessorService = paymentProcessorService;
    }

    @GetMapping
    public ResponseEntity<PaymentSummaryResponseDTO> getPaymentSummary(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        PaymentSummaryResponseDTO paymentSummaryResponseDTO = this.paymentProcessorService.getPaymentSummary(from, to);
        return ResponseEntity.ok(paymentSummaryResponseDTO);
    }

}
