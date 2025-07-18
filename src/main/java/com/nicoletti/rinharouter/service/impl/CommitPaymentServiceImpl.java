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

    private static final String SERVICE_TYPE_DEFAULT = "default";
    private static final String SERVICE_TYPE_FALLBACK = "fallback";

    @Value("${payment.service.url.default}")
    private String defaultPaymentServiceUrl;

    @Value("${payment.service.url.fallback}")
    private String fallbackPaymentServiceUrl;

    public CommitPaymentServiceImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public PaymentEntity commitPayment(PaymentEntity entity) {
//        logger.info("Committing payment: {}", entity);

        PaymentProcessorRequestDTO paymentProcessorRequestDTO = new PaymentProcessorRequestDTO(entity.getCorrelationId(), entity.getAmount(), entity.getCreatedAt().toString());
        logger.info("Preparing to commit payment with request: {}", paymentProcessorRequestDTO);

        // Prepare the request to the default payment service
        boolean status = restService.post(
                defaultPaymentServiceUrl,
                paymentProcessorRequestDTO,
                PaymentProcessorResponseDTO.class
        );

        // If the default service fails, try the fallback service
        if (!status) {
            logger.error("Failed to commit payment to default service, trying fallback service");
            status = restService.post(
                    fallbackPaymentServiceUrl,
                    paymentProcessorRequestDTO,
                    PaymentProcessorResponseDTO.class);
        }

        // Update the payment entity status based on the response
        if (status) {
            entity.setStatus(ProcessStatusType.COMPLETED);
            entity.setServicePaymentName(SERVICE_TYPE_DEFAULT);
        } else {
            entity.setStatus(ProcessStatusType.PENDING);
            entity.setServicePaymentName(SERVICE_TYPE_FALLBACK);
            entity.setRetryCount(entity.getRetryCount() + 1);
        }

        return entity;
    }


}
