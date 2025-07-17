package com.nicoletti.rinharouter.service.api;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;

public interface PaymentProcessorService {

    /**
     * Processes a payment request and returns a response.
     *
     * @param requestPaymentDTO the payment request data transfer object
     * @return the payment response data transfer object
     */
    PaymentResponseDTO processPayment(PaymentRequestDTO requestPaymentDTO);
}
