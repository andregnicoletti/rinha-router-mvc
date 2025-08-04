package com.nicoletti.rinharouter.service.api;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;
import com.nicoletti.rinharouter.model.dto.PaymentSummaryResponseDTO;
import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.entity.PaymentTransactionEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface PaymentService {

    /**
     * Processes a payment request and returns a response.
     *
     * @param requestPaymentDTO the payment request data transfer object
     * @return the payment response data transfer object
     */
    PaymentResponseDTO processPayment(PaymentRequestDTO requestPaymentDTO);

    List<PaymentEntity> findPendingPayments();

    void updatePaymentStatus(List<PaymentEntity> pendingPayments);

    PaymentSummaryResponseDTO getPaymentSummary(OffsetDateTime from, OffsetDateTime to);

    PaymentTransactionEntity savePaymentTransaction(PaymentTransactionEntity paymentTransactionEntity);
}
