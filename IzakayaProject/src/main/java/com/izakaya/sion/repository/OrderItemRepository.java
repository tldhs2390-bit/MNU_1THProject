package com.izakaya.sion.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.izakaya.sion.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("""
        select oi.menuName,
               coalesce(sum(oi.quantity), 0),
               coalesce(sum(oi.lineTotal), 0)
        from OrderItemEntity oi
        join oi.order o
        join o.payment p
        where o.store.id = :storeId
          and p.status = com.izakaya.sion.entity.PaymentStatus.PAID
          and p.paidAt >= :from and p.paidAt < :to
        group by oi.menuName
        order by sum(oi.quantity) desc
    """)
    List<Object[]> topMenusByQty(@Param("storeId") Long storeId,
                                 @Param("from") LocalDateTime from,
                                 @Param("to") LocalDateTime to);
}