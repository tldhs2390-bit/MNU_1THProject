package com.izakaya.sion.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.izakaya.sion.entity.PaymentEntity;
import com.izakaya.sion.entity.PaymentMethod;
import com.izakaya.sion.entity.PaymentStatus;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    // =========================================================
    // ✅ TopbarService / SummaryService / AdminStoreRealtimeService 에서 사용하는 합계/건수
    // PaymentEntity -> OrderEntity -> StoreEntity 조인으로 storeId 필터링
    // =========================================================

    @Query("""
        select coalesce(sum(p.paidAmount), 0)
        from PaymentEntity p
        join p.order o
        join o.store s
        where s.id = :storeId
          and p.status = :status
          and p.paidAt >= :from
          and p.paidAt < :to
    """)
    long sumPaidAmountByStoreAndPaidAtBetween(
            @Param("storeId") Long storeId,
            @Param("status") PaymentStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
        select coalesce(sum(p.paidAmount), 0)
        from PaymentEntity p
        join p.order o
        join o.store s
        where s.id = :storeId
          and p.status = :status
          and p.method = :method
          and p.paidAt >= :from
          and p.paidAt < :to
    """)
    long sumPaidAmountByStoreAndMethodAndPaidAtBetween(
            @Param("storeId") Long storeId,
            @Param("status") PaymentStatus status,
            @Param("method") PaymentMethod method,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
        select count(p)
        from PaymentEntity p
        join p.order o
        join o.store s
        where s.id = :storeId
          and p.status = :status
          and p.paidAt >= :from
          and p.paidAt < :to
    """)
    long countPaidByStoreAndPaidAtBetween(
            @Param("storeId") Long storeId,
            @Param("status") PaymentStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    // =========================================================
    // ✅ 매출 이력(날짜별) - PosSalesHistoryService 에서 사용
    // =========================================================

    @Query("""
        select p
        from PaymentEntity p
        join p.order o
        join o.store s
        where s.id = :storeId
          and p.status = :status
          and p.paidAt >= :start
          and p.paidAt < :end
        order by p.paidAt desc
    """)
    List<PaymentEntity> findSalesByStoreAndDateWithStatus(
            @Param("storeId") Long storeId,
            @Param("status") PaymentStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        select p
        from PaymentEntity p
        join p.order o
        join o.store s
        where s.id = :storeId
          and p.paidAt >= :start
          and p.paidAt < :end
        order by p.paidAt desc
    """)
    List<PaymentEntity> findSalesByStoreAndDateAllStatuses(
            @Param("storeId") Long storeId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}