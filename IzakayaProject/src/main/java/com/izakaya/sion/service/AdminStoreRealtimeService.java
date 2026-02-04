package com.izakaya.sion.service;

import java.util.EnumSet;

import org.springframework.stereotype.Service;

import com.izakaya.sion.admin.dto.AdminStoreTodayResponse;
import com.izakaya.sion.entity.PaymentStatus;
import com.izakaya.sion.entity.ReservationStatus;
import com.izakaya.sion.repository.PaymentRepository;
import com.izakaya.sion.repository.ReservationRepository;
import com.izakaya.sion.service.pos.PosBusinessTimeRange;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStoreRealtimeService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public AdminStoreTodayResponse todaySummary(Long storeId) {

        // ✅ 03:00 기준 "오늘 영업일" 범위로 변경
        var r = PosBusinessTimeRange.todayBusinessRange();

        long sales = paymentRepository.sumPaidAmountByStoreAndPaidAtBetween(
                storeId, PaymentStatus.PAID, r.from(), r.to()
        );

        long resvCount = reservationRepository.countByStoreIdAndVisitAtBetweenAndStatusIn(
                storeId, r.from(), r.to(), EnumSet.of(ReservationStatus.PENDING, ReservationStatus.ACCEPTED)
        );

        return new AdminStoreTodayResponse(storeId, sales, resvCount);
    }
}