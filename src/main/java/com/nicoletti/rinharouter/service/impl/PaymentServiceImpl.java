package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResultsDTO;
import com.nicoletti.rinharouter.model.dto.PaymentSummaryResponseDTO;
import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.entity.PaymentTransactionEntity;
import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import com.nicoletti.rinharouter.model.projection.PaymentSummaryProjection;
import com.nicoletti.rinharouter.repository.PaymentTransactionRepository;
import com.nicoletti.rinharouter.repository.PaymentsRepository;
import com.nicoletti.rinharouter.service.api.PaymentService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private PaymentsRepository paymentsRepository;
    private PaymentTransactionRepository paymentTransactionRepository;

    public PaymentServiceImpl(PaymentsRepository paymentsRepository,
                              PaymentTransactionRepository paymentTransactionRepository) {
        this.paymentsRepository = paymentsRepository;
        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO dto) {
        //TODO apenas salvar registro no banco de dados para processar depois

        PaymentEntity paymentEntity = new PaymentEntity().builder()
                .amount(dto.amount())
                .correlationId(dto.correlationId())
                .createdAt(OffsetDateTime.now())
                .retryCount(0)
                .status(ProcessStatusType.PENDING)
                .build();

        paymentsRepository.save(paymentEntity);

        return new PaymentResponseDTO(true, dto.correlationId(), dto.amount());
    }

    @Transactional
    @Override
    public List<PaymentEntity> findPendingPayments() {
        List<PaymentEntity> allPayments = paymentsRepository.findAllByStatus(ProcessStatusType.PENDING);
        allPayments.stream().forEach(pay -> pay.setStatus(ProcessStatusType.PROCESSING));
        paymentsRepository.saveAll(allPayments);
        return allPayments;
    }

    @Transactional
    @Override
    public void updatePaymentStatus(List<PaymentEntity> pendingPayments) {
        paymentsRepository.saveAll(pendingPayments);
    }

    @Override
    public PaymentSummaryResponseDTO getPaymentSummary(OffsetDateTime from, OffsetDateTime to) {

        logger.info("Retrieving payment summary from {} to {}", from, to);

        List<PaymentSummaryProjection> summaryList = paymentTransactionRepository.findSummaryByDateRange(from, to);
        logger.info("Payment summary: {}", summaryList);

        Map<String, PaymentResultsDTO> resultsMap = summaryList.stream()
                .collect(Collectors.toMap(
                        m -> m.getEndpointType(),
                        m -> new PaymentResultsDTO(m.getTotalRequests(), m.getTotalAmount())));

        return new PaymentSummaryResponseDTO(
                resultsMap.get("default") != null ? resultsMap.get("default") : new PaymentResultsDTO(0, 0.0),
                resultsMap.get("fallback") != null ? resultsMap.get("fallback") : new PaymentResultsDTO(0, 0.0)
        );
    }

    @Transactional
    @Override
    public PaymentTransactionEntity savePaymentTransaction(PaymentTransactionEntity paymentTransactionEntity) {
        return paymentTransactionRepository.save(paymentTransactionEntity);
    }

}
