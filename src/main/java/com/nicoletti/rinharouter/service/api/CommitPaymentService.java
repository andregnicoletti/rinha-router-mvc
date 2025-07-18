package com.nicoletti.rinharouter.service.api;

import com.nicoletti.rinharouter.model.entity.PaymentEntity;

public interface CommitPaymentService {

    PaymentEntity commitPayment(PaymentEntity paymentEntity);

}
