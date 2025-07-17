package com.nicoletti.rinharouter.repository;

import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentEntity, Long> {
}
