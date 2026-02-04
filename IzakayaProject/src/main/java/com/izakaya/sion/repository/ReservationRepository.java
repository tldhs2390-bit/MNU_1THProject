package com.izakaya.sion.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.izakaya.sion.admin.dto.PendingReservationView;
import com.izakaya.sion.entity.ReservationEntity;
import com.izakaya.sion.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    // 승인 대기
    List<PendingReservationView>
        findTop20ByStore_IdAndStatusOrderByCreatedAtDesc(
            Long storeId,
            ReservationStatus status
        );

    // 승인 완료 (受付済み予約)
    List<ReservationEntity>
        findByStore_IdAndStatusOrderByVisitAtAsc(
            Long storeId,
            ReservationStatus status
        );

    // 날짜별 예약 이력 (수락 / 거절 전부)
    @Query("""
        select r
        from ReservationEntity r
        join r.store s
        where s.id = :storeId
          and r.visitAt >= :start
          and r.visitAt < :end
        order by r.visitAt asc
    """)
    List<ReservationEntity> findByStoreIdAndVisitAtBetweenOrderByVisitAtAsc(
        @Param("storeId") Long storeId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    // 통계용 count
    @Query("""
        select count(r)
        from ReservationEntity r
        join r.store s
        where s.id = :storeId
          and r.visitAt >= :from
          and r.visitAt < :to
          and r.status in :statuses
    """)
    long countByStoreIdAndVisitAtBetweenAndStatusIn(
        @Param("storeId") Long storeId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to,
        @Param("statuses") Set<ReservationStatus> statuses
    );
}