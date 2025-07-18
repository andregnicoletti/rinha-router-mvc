package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;
import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import com.nicoletti.rinharouter.repository.PaymentsRepository;
import com.nicoletti.rinharouter.service.api.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentsRepository paymentsRepository;

    public PaymentServiceImpl(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO dto) {
        //TODO apenas salvar registro no banco de dados para processar depois

        PaymentEntity paymentEntity = new PaymentEntity().builder()
                .amount(dto.amount())
                .correlationId(dto.correlationId())
                .createdAt(LocalDateTime.now())
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

}
