package com.nicoletti.rinharouter.service.impl;

import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.entity.PaymentTransactionEntity;
import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import com.nicoletti.rinharouter.service.api.CommitPaymentService;
import com.nicoletti.rinharouter.service.api.PaymentJob;
import com.nicoletti.rinharouter.service.api.PaymentService;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentJobImpl implements PaymentJob {

    private static final Logger logger = LogManager.getLogger(PaymentJobImpl.class);

    private final PaymentService paymentService;

    private final CommitPaymentService commitPaymentService;

    public PaymentJobImpl(PaymentService paymentService,
                          CommitPaymentService commitPaymentService) {
        this.paymentService = paymentService;
        this.commitPaymentService = commitPaymentService;
    }

    @Scheduled(fixedDelay = 5000)
    private void paymentProcessors() {

        logger.info("Iniciando o serviço de execução de pagamentos...");

        //TODO: consultar banco de dados para verificar se há pagamentos pendentes
        //TODO: processar pagamentos pendentes
        //TODO: atualizar status dos pagamentos processados

        List<PaymentEntity> pendingPayments = paymentService.findPendingPayments();
        logger.info("Found {} pending payments", pendingPayments.size());
        pendingPayments.stream().forEach( request -> {
            logger.info("Processing payment with correlationId: {}", request.getCorrelationId());
            PaymentEntity paymentEntity = commitPaymentService.commitPayment(request);
            if(paymentEntity.getStatus().equals(ProcessStatusType.COMPLETED)) {
                logger.info("Payment with correlationId: {} completed successfully", paymentEntity.getCorrelationId());

                PaymentTransactionEntity paymentTransactionEntity = PaymentTransactionEntity.builder()
                        .correlationId(request.getCorrelationId())
                        .amount(request.getAmount())
                        .endpointType(request.getServicePaymentName())
                        .processedAt(request.getCreatedAt())
                        .build();
                paymentService.savePaymentTransaction(paymentTransactionEntity);


            } else {
                logger.error("Payment with correlationId: {} failed with status: {}", paymentEntity.getCorrelationId(), paymentEntity.getStatus());
            }
        });

        paymentService.updatePaymentStatus(pendingPayments);


    }

}
