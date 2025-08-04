package com.nicoletti.rinharouter.repository;

import com.nicoletti.rinharouter.model.projection.PaymentSummaryProjection;
import com.nicoletti.rinharouter.model.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {

    @Query("""
                SELECT 
                    p.endpointType AS endpointType,
                    COUNT(p) AS totalRequests,
                    COALESCE(SUM(p.amount), 0) AS totalAmount
                FROM PaymentTransactionEntity p
                WHERE
                    p.processedAt >= :from
                    AND p.processedAt <= :to
                GROUP BY p.endpointType
            """)
    List<PaymentSummaryProjection> findSummaryByDateRange(
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to
    );

}
