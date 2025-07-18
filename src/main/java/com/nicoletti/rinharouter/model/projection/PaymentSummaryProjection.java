package com.nicoletti.rinharouter.model.projection;

public interface PaymentSummaryProjection {

    String getEndpointType();

    long getTotalRequests();

    double getTotalAmount();

}
