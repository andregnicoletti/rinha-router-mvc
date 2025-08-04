package com.nicoletti.rinharouter.model.entity;

import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String correlationId;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private ProcessStatusType status;
    private OffsetDateTime createdAt;
    private Integer retryCount;
    private String servicePaymentName;

}
