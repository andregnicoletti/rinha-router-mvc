package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.model.dto.PaymentProcessorRequestDTO;
import com.nicoletti.rinharouter.model.dto.PaymentProcessorResponseDTO;
import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import com.nicoletti.rinharouter.service.api.CommitPaymentService;
import com.nicoletti.rinharouter.service.api.RestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommitPaymentServiceImpl implements CommitPaymentService {

    private static final Logger logger = LogManager.getLogger(CommitPaymentServiceImpl.class);

    private RestService restService;

    @Value("${payment.service.url.default}")
    private String defaultPaymentServiceUrl;

    @Value("${payment.service.url.fallback}")
    private String fallbackPaymentServiceUrl;

    public CommitPaymentServiceImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public PaymentEntity commitPayment(PaymentEntity entity) {
        logger.info("Committing payment: {}", entity);

        PaymentProcessorRequestDTO paymentProcessorRequestDTO = new PaymentProcessorRequestDTO(entity.getCorrelationId(), entity.getAmount(), entity.getCreatedAt().toString());

        try {
            PaymentProcessorResponseDTO post = restService.post(
                    defaultPaymentServiceUrl,
                    paymentProcessorRequestDTO,
                    PaymentProcessorResponseDTO.class
            );
            entity.setStatus(ProcessStatusType.COMPLETED);

        } catch (Exception e) {
            logger.error("Failed to commit payment to default service, trying fallback service {}", e.getMessage());
            try {
                PaymentProcessorResponseDTO post = restService.post(
                        fallbackPaymentServiceUrl,
                        paymentProcessorRequestDTO,
                        PaymentProcessorResponseDTO.class
                );
                entity.setStatus(ProcessStatusType.COMPLETED);

            } catch (Exception ex) {
                logger.error("Failed to commit payment to fallback service: {}", ex.getMessage());
                entity.setStatus(ProcessStatusType.PENDING);
                entity.setRetryCount(entity.getRetryCount() + 1);
            }
        }

        return entity;
    }


}
