package com.nicoletti.rinharouter.repository;

import com.nicoletti.rinharouter.model.entity.PaymentEntity;
import com.nicoletti.rinharouter.model.enuns.ProcessStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findAllByStatus(ProcessStatusType processStatusType);
}
