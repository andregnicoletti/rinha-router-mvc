package com.nicoletti.rinharouter.controller;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;
import com.nicoletti.rinharouter.service.api.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentProcessorService;

    public PaymentController(PaymentService paymentProcessorService) {
        this.paymentProcessorService = paymentProcessorService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO requestPaymentDTO) {
        PaymentResponseDTO paymentResponseDTO = this.paymentProcessorService.processPayment(requestPaymentDTO);
        return ResponseEntity.ok(paymentResponseDTO);
    }

}
