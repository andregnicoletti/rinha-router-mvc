package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.model.dto.PaymentRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentResponseDTO;
import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.repository.PaymentsRepository;
import com.nicoletti.rinharouter.service.api.PaymentProcessorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {

    private PaymentsRepository paymentsRepository;

    public PaymentProcessorServiceImpl(PaymentsRepository paymentsRepository) {
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
                .status(Boolean.FALSE)
                .build();

        paymentsRepository.save(paymentEntity);

        return new PaymentResponseDTO(true, dto.correlationId(), dto.amount());
    }

}
